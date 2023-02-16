describe('IPv4 Reverse', () => {
    beforeEach(() => {
      cy.visit('http://localhost:3000/Reverse')
    })
  
    it('IPv4: All Fields ', () => { 

    })
  
    it('IPv4: Only Country' ,() => { 

    })
  
    it('IPv4: Only Usage Type' ,() => { 

    })
  
    it('IPv4: Only ISP' ,() => { 

    })

    it('IPv4: Country + Usage' ,() => { 

    })

    it('IPv4: Country + ISP' ,() => { 

    })

    it('IPv4: ISP + Usage Type' ,() => { 

    })
})


describe('IPv6 Reverse', () => {
    beforeEach(() => {
      cy.visit('http://localhost:3000/Reverse')
      cy.get('#selectProtocol').parent().click().get('ul > li[data-value="IPv6"]').click()
    })

    it('IPv6: All Fields ', () => { 

    })
  
    it('IPv6: Only Country' ,() => { 

    })
  
    it('IPv6: Only Usage Type' ,() => { 

    })
  
    it('IPv6: Only ISP' ,() => { 

    })

    it('IPv6: Country + Usage' ,() => { 

    })

    it('IPv6: Country + ISP' ,() => { 

    })

    it('IPv6: ISP + Usage Type' ,() => { 

    })
})