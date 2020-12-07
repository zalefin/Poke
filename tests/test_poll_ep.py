import unittest
import requests
import json
from net_util import *


class TestPollEndpoint(unittest.TestCase):

    def test_get(self):
        r = requests.get(URL_POLL)
        self.assertEqual(r.status_code, 204)

    def test_normal(self):
        r = requests.post(URL_POLL, data={'user': UUID1})
        self.assertEqual(r.status_code, 200)
        jd = json.loads(r.content)
        self.assertIn('pokes', jd.keys())

    def test_badusr(self):
        r = requests.post(URL_POLL, data={'user': 'garbage'})
        self.assertEqual(r.status_code, 400)


if __name__ == '__main__':
    unittest.main()
