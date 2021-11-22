import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def jsonSlurper = new JsonSlurper()
def movie = jsonSlurper.parseText(new String(payload))

println movie

JsonOutput.toJson(movie)
