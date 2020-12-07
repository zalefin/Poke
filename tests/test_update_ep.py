import unittest
import requests
import json
from net_util import *


class TestUpdateEndpoint(unittest.TestCase):

    def test_get(self):
        r = requests.get(URL_UPDA)
        self.assertEqual(r.status_code, 204)

    def test_normal(self):
        r = requests.post(URL_UPDA, data={'user': UUID1})
        self.assertEqual(r.status_code, 200)
        jd = json.loads(r.content)
        self.assertIn('name', jd.keys())
        self.assertIn('friends', jd.keys())

    def test_badusr(self):
        r = requests.post(URL_UPDA, data={'user': 'garbage'})
        self.assertEqual(r.status_code, 400)

    def test_nousr(self):
        r = requests.post(URL_UPDA, data={})
        self.assertEqual(r.status_code, 400)

if __name__ == '__main__':
    unittest.main()
