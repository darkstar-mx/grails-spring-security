package com.myapp

import grails.plugin.springsecurity.annotation.Secured

class ProductAnnouncementController {

    @Secured(value=["hasRole('ROLE_ADMIN')"])
    def index() {
        def announcements = ProductAnnouncement.createCriteria().list {
            order("dateCreated", "desc")
            maxResults(1)
        }
        render announcements.first()?.message
    }
}
