from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from uuid import uuid4
from datetime import datetime
import pytz
import json
from collections import defaultdict

from .models import User, Friend


POKE_QUEUE = defaultdict(list)

VALID_PAYLOADS = [str(v) for v in range(0,4)] # {0,...,3} are valid payloads

def _nowlocal():
    dt_naive = datetime.now()
    tz = pytz.timezone('America/Denver') # Use timezone metadata
    return tz.localize(dt_naive)


def _must_be_POST(f):
    def inner(request, *args, **kwargs):
        if request.method == 'POST':
            return f(request, *args, **kwargs)
        else:
            return HttpResponse('requires POST', status=204) # return 204 - no content
    return inner


def _requires_POST_args(*names):
    def dec(f):
        @_must_be_POST
        def inner(request, *args, **kwargs):
            try:
                [request.POST[n] for n in names] # evaluate args fish for bad entries
            except KeyError:
                return HttpResponse('wrong args', status=400) # return 400 - bad request

            return f(request, *args, **kwargs)
        return inner
    return dec


@csrf_exempt
@_requires_POST_args('user', 'target', 'payload')
def do_poke_inbound(request):
    user = request.POST['user']
    target = request.POST['target']
    payload = request.POST['payload']
    # Confirm user exists and user is friends with target
    if not User.objects.filter(uuid=user).exists():
        return HttpResponse('user invalid', status=400)

    if not Friend.objects.filter(user_uuid=user, friend_uuid=target).exists():
        return HttpResponse('target invalid', status=400)

    if not payload in VALID_PAYLOADS:
        return HttpResponse('payload invalid', status=400)

    POKE_QUEUE[target].append([user, payload])
    friend_record = Friend.objects.get(user_uuid=user, friend_uuid=target)
    friend_record.total_pokes += 1
    friend_record.save() # commit row change to db
    return HttpResponse('success')


@csrf_exempt
@_requires_POST_args('name')
def register(request):
    name = request.POST['name']

    if len(name) > 32:
        return HttpResponse('name invalid', status=400)

    uuid = str(uuid4()) # generate uuid for registration
    dt_aware = _nowlocal()
    User.objects.create(uuid=uuid, name=name, reg_date=dt_aware) # commit to database
    return HttpResponse(uuid)


@csrf_exempt
@_requires_POST_args('user')
def poll(request):
    user = request.POST['user']

    if not User.objects.filter(uuid=user).exists():
        return HttpResponse('user invalid', status=400)

    dat = {
            'pokes': POKE_QUEUE.pop(user) if user in POKE_QUEUE.keys() else [],
            }
    return HttpResponse(json.dumps(dat))


@csrf_exempt
@_requires_POST_args('user')
def update(request):
    user = request.POST['user']

    if not User.objects.filter(uuid=user).exists():
        return HttpResponse('user invalid', status=400)

    friend_uuids = [v['friend_uuid'] for v in Friend.objects.filter(user_uuid=user).values('friend_uuid')]
    friend_names = list(map(lambda friend_uuid: User.objects.filter(uuid=friend_uuid).values('name')[0]['name'], friend_uuids))
    dat = {
            'name': User.objects.filter(uuid=user).values('name')[0]['name'], # TODO see if there is a nicer way that this
            'friends': list(zip(friend_names, friend_uuids)),
            }
    return HttpResponse(json.dumps(dat))


@csrf_exempt
@_requires_POST_args('user', 'target')
def add_friend(request):
    user = request.POST['user']
    target = request.POST['target']

    if not User.objects.filter(uuid=user).exists():
        return HttpResponse('user invalid', status=400)

    if not User.objects.filter(uuid=target).exists():
        return HttpResponse('target invalid', status=400)

    if Friend.objects.filter(user_uuid=user,friend_uuid=target).exists(): #already friends case
        return HttpResponse('friend pair exists', status=400)

    dt_aware = _nowlocal() #new friends case
    Friend.objects.create(user_uuid=user, friend_uuid=target, added_date=dt_aware, total_pokes=0)
    Friend.objects.create(user_uuid=target, friend_uuid=user, added_date=dt_aware, total_pokes=0)
    return HttpResponse("success")

@csrf_exempt
@_requires_POST_args('user', 'target')
def delete_friend(request):
    user = request.POST['user']
    target = request.POST['target']

    if not User.objects.filter(uuid=user).exists():
        return HttpResponse('user invalid', status=400)

    if not User.objects.filter(uuid=target).exists():
        return HttpResponse('target invalid', status=400)

    if not Friend.objects.filter(user_uuid=user,friend_uuid=target).exists(): #existing friends case
        return HttpResponse('not friend pair exists', status=400)

    Friend.objects.filter(user_uuid=user, friend_uuid=target).delete() #dt aware and pokes included?
    Friend.objects.filter(user_uuid=target, friend_uuid=user).delete()
    return HttpResponse("success")

