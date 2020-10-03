from django.shortcuts import render
from django.http import HttpResponse
from uuid import uuid4
from datetime import datetime
import pytz

from .models import User

def register(request, name):
    uuid = str(uuid4()) # generate uuid for registration
    dt_naive = datetime.now()
    tz = pytz.timezone('America/Denver') # Use timezone metadata
    dt_aware = tz.localize(dt_naive)
    User.objects.create(uuid=uuid, name=name, reg_date=dt_aware) # commit to database
    return HttpResponse(uuid)


def add_friend(request, user, target):
    return HttpResponse("success")

