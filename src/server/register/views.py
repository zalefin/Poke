from django.shortcuts import render
from django.http import HttpResponse
from uuid import uuid4
from datetime import datetime

from .models import User

def register(request, name):
    uuid = str(uuid4()) # generate uuid for registration
    User.objects.create(uuid=uuid, name=name, reg_date=datetime.now()) # commit to database
    return HttpResponse(uuid)

