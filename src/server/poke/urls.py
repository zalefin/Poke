from django.urls import path, re_path

from . import views

urlpatterns= [
        path('register/name=<name>', views.register, name='register'),
        path('<user>/friends/add/uuid=<target>', views.add_friend, name='add_friend'),
        ]
