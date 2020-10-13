from django.urls import path, re_path

from . import views

urlpatterns= [
        path('register', views.register, name='register'),
        path('friends/add', views.add_friend, name='add_friend'),
        path('update', views.update, name='update'),
        path('poll', views.poll, name='poll'),
        path('poke', views.do_poke_inbound, name='do_poke_inbound'),
        ]
