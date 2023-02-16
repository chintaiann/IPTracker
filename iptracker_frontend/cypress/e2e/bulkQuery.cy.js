
const validIPv4String = "4.2.80.1,4.2.85.3,4.2.85.89,4.2.85.34,1.1.1.1,8.8.8.8"
const invalidIPv4String = "4.2.80.1,4.2.85.3,4.2.89,4.2.85.34,1.1.1.1,8.8.8.8"
const validIPv6String = "::ffff:1.0.16.0,::ffff:1.0.209.0,2001:db8:3333:4444:5555:6666:7777:8888,::ffff:1.11.190.0"
const invalidIPv6String =  "::ffff:1.0.16.0,::ffff:1.0.209.0,2001:db8:3333:4444:5555:6666:7777:8888,::ffff:1.11.190.0,invalid"


describe('IPv4 Bulk Query', () => {
    beforeEach(() => {
      cy.visit('http://localhost:3000/Bulk')
    })


    it("correct bulk query of IPv4 in string", () => { 
        cy.get("[id=ipField]").click().type(validIPv4String)
        cy.get("[id=submitBulkQuery]").click();
        cy.get("[id=Page]").contains("4.2.80.1 is from United States of America")
        cy.get("[id=Page]").contains("4.2.85.3 is from United States of America")    
        cy.get("[id=Page]").contains("4.2.85.89 is from United States of America")       
        cy.get("[id=Page]").contains("4.2.85.34 is from United States of America")        
        cy.get("[id=Page]").contains("1.1.1.1 cannot be found!")
        cy.get("[id=Page]").contains("8.8.8.8 cannot be found!")
    })

    it("empty bulk query of IPv4 in string", () => { 
        
    })


    it("at least one IP is invalid format", () => { 
        cy.get("[id=ipField]").click().type(invalidIPv4String)
        cy.get("[id=submitBulkQuery]").click();
        cy.get("[id=Page]").contains("IPv4 is not of valid format")
    })

    it("upload of TXT file of valid IPv4 addresses", () => { 
        cy.get("[id=uploadTXT]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/bulkIPv4format.txt')
        cy.get("[id=Page]").contains("4.2.80.1 is from United States of America")
        cy.get("[id=Page]").contains("4.2.85.3 is from United States of America")    
        cy.get("[id=Page]").contains("1.1.1.1 cannot be found!")
        cy.get("[id=Page]").contains("4.2.85.89 is from United States of America")       

    })
    it("upload of TXT file of invalid IPv4 address", () => { 
        cy.get("[id=uploadTXT]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/invalidbulkIPv4.txt')
        cy.get("[id=Page]").contains("IPv4 is not of valid format")

    })


    it("upload of JSON file with correct fieldName and valid IPv4 address", () => { 
        cy.get("[id=jsonField]").click().type("ip");
        cy.get("[id=uploadJSON]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/jsonIPv4.json')
        cy.get("[id=Page]").contains("4.2.80.1 is from United States of America")
        cy.get("[id=Page]").contains("4.2.85.3 is from United States of America")    
        cy.get("[id=Page]").contains("1.1.1.1 cannot be found!")
    })
    it("upload of JSON file with correct fieldname, but invalid IPv4 address", () => { 
        cy.get("[id=jsonField]").click().type("ip");
        cy.get("[id=uploadJSON]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/invalidjsonIPv4.json')
        cy.get("[id=Page]").contains("IPv4 is not of valid format")

    })

    it("uploading of JSON file without specifying fieldname", () => { 
        cy.get("[id=uploadJSON]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/jsonIPv4.json')
        cy.get("[id=Page]").contains("Please enter JSON field")

    })

    it("uploading of JSON file with a non-existent fieldName", () => { 
        cy.get("[id=jsonField]").click().type("does not even exist");
        cy.get("[id=uploadJSON]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/jsonIPv4.json')
        cy.get("[id=Page]").contains("JSON field doesn't exist")

    })

    it("uploading of JSON file, but JSON is not valid format.", () => { 
        cy.get("[id=jsonField]").click().type("ip");
        cy.get("[id=uploadJSON]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/bulkIPv4format.txt')
        cy.get("[id=Page]").contains("Invalid JSON format.")
    })
})


describe("IPv6 Bulk Query", () => { 
    beforeEach(() => {
        cy.visit('http://localhost:3000/Bulk')
        cy.get('#selectProtocol').parent().click().get('ul > li[data-value="IPv6"]').click()
    })

    it("correct bulk query of IPv6 in string" , () =>  { 
        cy.get("[id=ipField]").click().type(validIPv6String)
        cy.get("[id=submitBulkQuery]").click();
        cy.get("[id=Page]").contains("::ffff:1.0.16.0 is from Japan")
        cy.get("[id=Page]").contains("::ffff:1.0.209.0 is from Thailand")
        cy.get("[id=Page]").contains("2001:db8:3333:4444:5555:6666:7777:8888 cannot be found!")
        cy.get("[id=Page]").contains("::ffff:1.11.190.0 is from Korea, Republic of")


    })

    it("invalid bulk query of IPv6 in string" , () =>  { 
        cy.get("[id=ipField]").click().type(invalidIPv6String)
        cy.get("[id=submitBulkQuery]").click();
        cy.get("[id=Page]").contains("IPv6 is not of valid format")

    })

    it("valid upload of TXT file of IPv6" , () =>  { 
        cy.get("[id=uploadTXT]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/bulkIPv6.txt')
        cy.get("[id=Page]").contains("::ffff:1.0.16.0 is from Japan. ISP: I2TS Inc. Usage type: DCH")
        cy.get("[id=Page]").contains("::ffff:1.0.209.0 is from Thailand. ISP: TOT Public Company Limited Usage type: ISP/MOB")    
        cy.get("[id=Page]").contains("2001:db8:3333:4444:5555:6666:7777:8888 cannot be found!")
        cy.get("[id=Page]").contains("::ffff:1.11.190.0 is from Korea, Republic of. ISP: CJ Hello Co. Ltd. Usage type: ISP")
        cy.get("[id=Page]").contains("::ffff:1.8.18.0 is from China. ISP: Internet Domain Name System Beijing Engineering Resrarch Center Ltd. Usage type: ISP")    
    })

    it("invalid upload of TXT file of IPv6" , () =>  { 
        cy.get("[id=uploadTXT]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/invalidBulkIPv6.txt')
        cy.get("[id=Page]").contains("IPv6 is not of valid format.")

    })

    it("valid upload of JSON file of IPv6" , () =>  { 
        cy.get("[id=jsonField]").click().type("ip");
        cy.get("[id=uploadJSON]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/jsonIPv6.json')
        cy.get("[id=Page]").contains("::ffff:1.8.18.0 is from China. ISP: Internet Domain Name System Beijing Engineering Resrarch Center Ltd. Usage type: ISP")
        cy.get("[id=Page]").contains("::ffff:1.0.209.0 is from Thailand. ISP: TOT Public Company Limited Usage type: ISP/MOB")    
        cy.get("[id=Page]").contains("2001:db8:3333:4444:5555:6666:7777:8888 cannot be found!") 
    })

    it("invalid upload of JSON file of IPv6" , () =>  { 
        cy.get("[id=jsonField]").click().type("ip");
        cy.get("[id=uploadJSON]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/invalidJsonIPv6.json')
        cy.get("[id=Page]").contains("IPv6 is not of valid format.")

    })
    it("valid upload but missing field name of JSON file of IPv6" , () =>  { 
        cy.get("[id=uploadJSON]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/jsonIPv6.json')
        cy.get("[id=Page]").contains("Please enter JSON field to read the IP address before uploading.")
    })


    it("valid upload but non-existent field name of JSON file of IPv6" , () =>  { 
        cy.get("[id=jsonField]").click().type("does not exist");
        cy.get("[id=uploadJSON]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/jsonIPv6.json')
        cy.get("[id=Page]").contains("JSON field doesn't exist")
    })
    it("uploading of JSON file, but JSON is not valid format.", () => { 
        cy.get("[id=jsonField]").click().type("ip");
        cy.get("[id=uploadJSON]").selectFile('/Users/chintaiann/Documents/GitHub/IPTracker/iptracker_frontend/cypress/fixtures/bulkIPv6.txt')
        cy.get("[id=Page]").contains("Invalid JSON format.")
    })
})