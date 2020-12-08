# CSCI 3308 - Fall 2020

## Poke!
Poke! is a social media app about poking your friends.
Send a small, predefined message with a fun image to a friend (they'll be notified!).
One considerable difference between Poke! and most social media apps is that in
Poke!, friends can *only* be added using someone elses QR code.
This means that those on your friends list will consist exclusively of those
you've connected with in person! (unless you cheat :O)
Start having fun poking your friends today!

## Team members:

- Jake Nichols
- Helen Kim
- Max Graef
- Zachary Lefin
- Kyle Baird
- Jackson Rini


# Running the Android Client
The Android client can be run simply by downloading the APK located in the most recent
release on GitHub.

# Running the Server
__Note:__ the app is currently hosted on a dedicated server and the source code of both the Android client
and the server must be changed in order to reflect your target locations.
Additionally, your own database must be reflected in `src/server/pokeserver/settings.py`

Assuming you have [Python3](https://www.python.org/downloads/) and the [Django](https://pypi.org/project/Django/) framework
package installed,

`python3 [PROJECTDIR]/src/server/manage.py runserver`

can be used to run the development server.

# Building/Running
The source can be built, run, and tested from Android Studio.

# Directory Structure
```
.
├── MILESTONES (Project milestones)
├── pushpi.sh
├── README.md (This file)
├── src
│   ├── PokeApp (Android client)
│   ├── server (Server source)
│   └── static
└── tests (Unit tests)

532 directories, 1229 files
```
