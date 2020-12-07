import unittest
import requests
from net_util import *


class TestPokeEndpoint(unittest.TestCase):

    def test_get(self):
        r = requests.get(URL_POKE)
        self.assertEqual(r.status_code, 204)

    def test_normal(self):
        # ensure users are friends (OK if this fails here)
        requests.post(URL_ADDF, data={'user': UUID1, 'target': UUID2})
        # do poke
        r = requests.post(URL_POKE, data={'user': UUID1, 'target': UUID2, 'payload': '1'})
        self.assertEqual(r.status_code, 200)
        self.assertEqual(r.content, b'success')

    def test_not_friends(self):
        # ensure users are NOT friends (OK if this fails here)
        requests.post(URL_DELF, data={'user': UUID1, 'target': UUID2})
        # do poke
        r = requests.post(URL_POKE, data={'user': UUID1, 'target': UUID2, 'payload': '1'})
        self.assertEqual(r.status_code, 400)

    def test_badusr(self):
        r = requests.post(URL_POKE, data={'user': 'garbage', 'target': UUID2, 'payload': '1'})
        self.assertEqual(r.status_code, 400)

    def test_badtar(self):
        r = requests.post(URL_POKE, data={'user': UUID1, 'target': 'garbage', 'payload': '1'})
        self.assertEqual(r.status_code, 400)

    def test_badpl(self):
        r = requests.post(URL_POKE, data={'user': UUID1, 'target': UUID2, 'payload': 'hi mom'})
        self.assertEqual(r.status_code, 400)

    def test_nousr(self):
        r = requests.post(URL_POKE, data={'target': UUID2, 'payload': '1'})
        self.assertEqual(r.status_code, 400)

    def test_notar(self):
        r = requests.post(URL_POKE, data={'user': UUID1, 'payload': '1'})
        self.assertEqual(r.status_code, 400)

    def test_nopl(self):
        r = requests.post(URL_POKE, data={'user': UUID1, 'target': UUID2})
        self.assertEqual(r.status_code, 400)


if __name__ == '__main__':
    unittest.main()
