from django.shortcuts import render
from django.http import HttpResponse
from uuid import uuid4
from datetime import datetime
import pytz

from .models import User, Friend


def _nowlocal():
    dt_naive = datetime.now()
    tz = pytz.timezone('America/Denver') # Use timezone metadata
    return tz.localize(dt_naive)


def register(request, name):
    uuid = str(uuid4()) # generate uuid for registration
    dt_aware = _nowlocal()
    User.objects.create(uuid=uuid, name=name, reg_date=dt_aware) # commit to database
    return HttpResponse(uuid)


def add_friend(request, user, target):
    if Friend.objects.filter(user_uuid=user,friend_uuid=target).exists(): #already friends case
        return HttpResponse("Already friends :P")
    else:
        dt_aware = _nowlocal() #new friends case 
        Friend.objects.create(user_uuid=user, friend_uuid=target, added_date=dt_aware, total_pokes=0)
        Friend.objects.create(user_uuid=target, friend_uuid=user, added_date=dt_aware, total_pokes=0)
        return HttpResponse("success")
    
