
//test correct input 
describe('IPv4 Single Query', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000/Single')
  })

  it('correct input of IPv4', () => { 
    cy.get('[id=ipField]').click().type("4.2.80.1")
    cy.get('[id=singleQuerySubmit]').click()
    cy.get('[id=Page]').contains("4.2.80.1 is from United States of America")
  })

  it('empty input of IPv4' ,() => { 
    cy.get('[id=singleQuerySubmit]').click()
  })

  it('invalid input of IPv4' ,() => { 
    cy.get('[id=ipField]').click().type("thisisnonsense.com@@@@!")
    cy.get('[id=singleQuerySubmit]').click()
    cy.get('[id=Page]').contains("IPv4 is not of valid format.");
  })

  it('valid input IPv4 but not found' ,() => { 
    cy.get('[id=ipField]').click().type("1.1.1.1")
    cy.get('[id=singleQuerySubmit]').click()
    cy.get('[id=Page]').contains("Sorry, IP was not found in database.");
  })











})


describe('IPv6 Single Query' , ()=> { 
  beforeEach(() => {
    cy.visit('http://localhost:3000/Single')
    cy.get('#selectProtocol').parent().click().get('ul > li[data-value="IPv6"]').click()

  })

  it('correct input of IPv6', () => {
    cy.get('[id=ipField]').click().type("::ffff:1.20.43.0")
    cy.get('[id=singleQuerySubmit]').click()
    cy.get('[id=Page]').contains("::ffff:1.20.43.0 is from Thailand")
  })

  it('empty input of IPv6' ,() => { 
    cy.get('[id=singleQuerySubmit]').click()
  })

  it('invalid input of IPv6' ,() => { 
    cy.get('[id=ipField]').click().type("thisisnonsense.com@@@@!")
    cy.get('[id=singleQuerySubmit]').click()
    cy.get('[id=Page]').contains("IPv6 is not of valid format.");
  })

  it('valid input IPv6 but not found' ,() => { 
    cy.get('[id=ipField]').click().type("2001:db8:3333:4444:5555:6666:7777:8888")
    cy.get('[id=singleQuerySubmit]').click()
    cy.get('[id=Page]').contains("Sorry, IP was not found in database.");
  })
})

//test empty input 


//test invalid IP format 


//test valid, but not found in database 



//same tests but for ipv6