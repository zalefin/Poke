#!/usr/bin/env python3
from uuid import UUID
from threading import Thread
from cli_client import Client


def add_dummy_friends(u, n):
    """Script function to add n: int friends to u: UUID"""
    clients = [Client(None, True, 'poke.zachlef.in') for _ in range(n)]
    def act(client, name, friend):
        client.register(name)
        client.add_friend(u)
    threads = [Thread(target=act, args=[client, 'client{}'.format(i), u]) for i, client in enumerate(clients)]
    for thread in threads: thread.start()
    for thread in threads: thread.join()
    for client in clients: print(client.uuid)


if __name__ == '__main__':
    u = input('Your UUID> ')
    n = int(input('Number of friends to generate/add> '))
    add_dummy_friends(u, n)

