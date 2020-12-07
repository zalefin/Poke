import unittest
import requests
from net_util import *


class TestRegisterEndpoint(unittest.TestCase):

    def test_get(self):
        r = requests.get(URL_REGI)
        self.assertEqual(r.status_code, 204)

    def test_normal(self):
        r = requests.post(URL_REGI, data={'name': 'JoeBob'})
        self.assertEqual(r.status_code, 200)
        self.assertTrue(len(r.content) > 0)

    def test_empty(self):
        r = requests.post(URL_REGI, data={'name': ''})
        self.assertEqual(r.status_code, 400)

    def test_large(self):
        r = requests.post(URL_REGI, data={'name': 'a'*33})
        self.assertEqual(r.status_code, 400)

    def test_maxsize(self):
        r = requests.post(URL_REGI, data={'name': 'a'*32})
        self.assertEqual(r.status_code, 200)
        self.assertTrue(len(r.content) > 0)


if __name__ == '__main__':
    unittest.main()
