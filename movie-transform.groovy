import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def jsonSlurper = new JsonSlurper()
def movie = jsonSlurper.parseText(new String(payload))

println movie

def connection = new URL( "https://imdb-internet-movie-database-unofficial.p.rapidapi.com/film/${movie.id}")
                 .openConnection() as HttpURLConnection

connection.setRequestProperty( 'x-rapidapi-host', 'imdb-internet-movie-database-unofficial.p.rapidapi.com' )
connection.setRequestProperty( 'x-rapidapi-key', 'f5db1bd94dmsh3e95994a440123ap12f61djsna14efa81e0f2') 
connection.setRequestProperty( 'User-Agent', 'GroovyScript')
connection.setRequestProperty( 'Accept', 'application/json' )
connection.setRequestProperty( 'Content-Type', 'application/json')

if ( connection.responseCode == 200 ) {
    
    def imdb = connection.inputStream.withCloseable { inStream ->
        new JsonSlurper().parse( inStream as InputStream )
    }
    
    movie.imdb = [ "rating": imdb.rating, "ratingCount": imdb.rating_votes ]    

} else {
    println connection.responseCode + ": " + connection.inputStream.text
}

println "[AF]" + movie

JsonOutput.toJson(movie)
