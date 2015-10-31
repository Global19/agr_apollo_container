package org.bbop.apollo

import grails.converters.JSON
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

@Transactional(readOnly = true)
class BookmarkController {

//    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def permissionService
    def preferenceService
    def projectionService
    def bookmarkService

    def list() {
        println "loading bookmark . . . "
        JSONObject bookmarkJson = (request.JSON ?: JSON.parse(params.data.toString())) as JSONObject
        User user = permissionService.getCurrentUser(bookmarkJson)
        Organism organism = preferenceService.getCurrentOrganism(user)

        render user.bookmarks as JSON
    }

    def getBookmark(){
        JSONObject bookmarkObject = (request.JSON ?: JSON.parse(params.data.toString())) as JSONObject
        User user = permissionService.currentUser
        Organism organism = preferenceService.getCurrentOrganism(user)

        // creates a projection based on the Bookmarks and caches them
        bookmarkObject.organism = organism.commonName
        projectionService.getProjection(bookmarkObject)

        render bookmarkObject as JSON
    }

    @Transactional
    def addBookmark() {
        JSONArray bookmarkArray = (request.JSON ?: JSON.parse(params.data.toString())) as JSONArray
        User user = permissionService.currentUser
//        Organism organism = preferenceService.getCurrentOrganism(user)
//        println "bookmark array ${bookmarkArray as JSON}"
        Bookmark bookmark = bookmarkService.convertJsonToBookmark(bookmarkArray.getJSONObject(0))
        render bookmark as JSON
    }

    @Transactional
    def deleteBookmark() {
        JSONArray bookmarkJson = (request.JSON ?: JSON.parse(params.data.toString())) as JSONArray
        User user = permissionService.getCurrentUser(new JSONObject())
        Organism organism = preferenceService.getCurrentOrganism(user)
        println "trying to delete bookmarkJSON ${bookmarkJson as JSON}"

        def idList = []
        for(int i = 0 ; i < bookmarkJson.size() ; i++){
            idList.add(bookmarkJson.getJSONObject(i).id)
        }

        Bookmark.deleteAll(Bookmark.findAllByIdInList(idList))

        def bookmarks = Bookmark.findAllByUserAndOrganism(user,organism)
        render bookmarks as JSON
    }
}
