#!/usr/bin/python

import json
import time
import pymongo
from os import getenv
from pymongo import MongoClient

def log(msg, level='INFO'):
    print(json.dumps({'message': msg, 'level': level}))

def LOG(start_msg):
    def function(fn):
        def decorator():
            log(start_msg)
            ini = time.time()
            fn()
            end = time.time()
            formatted = time.strftime('%H:%M:%S', time.gmtime(end - ini))
            log('%s | elapsed time: %s' % (start_msg, formatted))
        return decorator
    return function

def init_mongo():
    host = getenv('ZE_MONGO_URI')
    return MongoClient(host=host).partners_database
mongo = init_mongo()

@LOG('Creating MONGO indexes')
def create_mongo_indexes():
    ASC = pymongo.ASCENDING
    GEO = pymongo.GEOSPHERE
    mongo.partners.create_index([('document', ASC)], unique=True)
    mongo.partners.create_index([('address', GEO)])
    mongo.partners.create_index([('coverageArea', GEO)])
    log(mongo.partners.index_information())

log('Migration Started')

if getenv('CREATE_INDEXES') == 'true':
    create_mongo_indexes()

log('Migration Finished')
