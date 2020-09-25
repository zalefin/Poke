from django.db import models

class User(models.Model):
    uuid = models.CharField(max_length=36)
    name = models.CharField(max_length=36)
    reg_date = models.DateTimeField('date registered')
