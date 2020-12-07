#!/usr/bin/env python3
from uuid import UUID
from threading import Thread
import json
from cli_client import Client

def delete_all_friends(u):
    """Script function to delete all friends of u: UUID"""
    client = Client(u, True, 'poke.zachlef.in')
    friends = json.loads(client.update())['friends']
    client.friends = [UUID(uuid) for name, uuid in friends]
    for name, uuid in friends:
        Thread(target=lambda: client.delete_friend(UUID(uuid))).start()
        print('Remove', name, uuid)

if __name__ == "__main":
    u = input('Your UUID> ')
    delete_all_friends(u)
