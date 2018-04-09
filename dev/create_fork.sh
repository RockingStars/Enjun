#!/usr/bin/env bash
git remote add upstream https://github.com/RockingStars/Reversi.git
git fetch upstream
git merge upstream/master
git push
