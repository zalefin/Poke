import requests
from cli_client import POKEHOST

UUID1 = '09ef8488-6aed-4ce2-bde6-586d821c4aae'
UUID2 = '30708cec-2bf1-4ffd-9754-7e9a4e1fa48f'

URL_REGI = 'https://{}/poke/register'.format(POKEHOST)
URL_ADDF = 'https://{}/poke/friends/add'.format(POKEHOST)
URL_DELF = 'https://{}/poke/friends/delete'.format(POKEHOST)
URL_UPDA = 'https://{}/poke/update'.format(POKEHOST)
URL_POLL = 'https://{}/poke/poll'.format(POKEHOST)
URL_POKE = 'https://{}/poke/poke'.format(POKEHOST)
