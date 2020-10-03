from django.contrib import admin

from .models import User, Friend

@admin.register(User)
class UserAdmin(admin.ModelAdmin):
    list_display = ['uuid', 'name', 'reg_date']


@admin.register(Friend)
class FriendAdmin(admin.ModelAdmin):
    list_display = ['user_uuid', 'friend_uuid', 'added_date', 'total_pokes']
