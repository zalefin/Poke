from django.urls import path, re_path

from . import views

urlpatterns= [
        path('register', views.register, name='register'),
        path('friends/add', views.add_friend, name='add_friend'),
        path('friends/delete', views.delete_friend, name='delete_friend'),
        path('update', views.update_poll, name='update_poll'),
        ]
