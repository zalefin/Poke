#!/usr/bin/env python3
from uuid import UUID
from threading import Thread
from cli_client import Client
u = input('Your UUID> ')
n = int(input('Number of friends to generate/add> '))
clients = [Client(None, True, 'poke.zachlef.in') for _ in range(n)]
def act(client, name, friend):
    client.register(name)
    client.add_friend(u)
threads = [Thread(target=act, args=[client, 'client{}'.format(i), u]) for i, client in enumerate(clients)]
for thread in threads: thread.start()
for thread in threads: thread.join()
for client in clients: print(client.uuid)
