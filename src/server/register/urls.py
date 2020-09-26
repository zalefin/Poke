from django.urls import path, re_path

from . import views

urlpatterns= [
        path('name=<name>', views.register, name='register'),
        ]
