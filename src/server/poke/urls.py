from django.urls import path, re_path

from . import views

urlpatterns= [
        path('register', views.register, name='register'),
        path('friends/add', views.add_friend, name='add_friend'),
        ]
