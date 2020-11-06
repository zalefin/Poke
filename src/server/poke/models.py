from django.db import models

class User(models.Model):
    uuid = models.CharField(max_length=36)
    name = models.CharField(max_length=36)
    reg_date = models.DateTimeField('date registered')


class Friend(models.Model):
    user_uuid = models.CharField(max_length=36)
    friend_uuid = models.CharField(max_length=36)
    added_date = models.DateTimeField('added_date')
    total_pokes = models.IntegerField('total_pokes')

