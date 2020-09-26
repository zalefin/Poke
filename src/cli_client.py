#!/bin/env python3
import requests
from argparse import ArgumentParser
from uuid import UUID

class Client:
    def __init__(self, uuid):
        self.uuid = None
        self._set_uuid(uuid)
        self.name = None
        
    def _set_uuid(self, uuid):
        if uuid:
            UUID(uuid) # will throw error if invalid
            self.uuid = uuid

    def register(self, host, name):
        r = requests.get('http://{}/register/name={}'.format(host,
            name))
        c = r.content
        self._set_uuid(str(c, 'utf-8'))

    def poll(self):
        pass

    def poke(self, target_uuid):
        pass

if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument('--host', default='localhost:8000', type=str, nargs=1, help='host address')
    parser.add_argument('uuid', default=None, type=str, nargs='?', help='client uuid')
    args = parser.parse_args()
    client = Client(args.uuid)
    print('h for help')
    while True:
        if not client.uuid: print('WARNING: no uuid set')
        uin = input('> ')
        if uin == 'S':
            try:
                client._set_uuid(input('Enter UUID> '))
            except:
                print('Invalid UUID: set failed')
                continue
        elif uin == 's':
            print('UUID: {}'.format(client.uuid))
        elif uin == 'r':
            name = input('Enter name> ')
            try:
                client.register(args.host, name)
            except:
                print('Register failed')
        elif uin == 'P':
            print('no implement')
        elif uin == 'p':
            print('no implement')
        elif uin == 'h':
            print('S: Set UUID\ns: Show UUID\nr: Register\nP: Poll\np: Poke\nh: Help\nq: Quit')
        elif uin == 'q':
            break
        else:
            print('Invalid option')

