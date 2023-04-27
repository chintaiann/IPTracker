//protocols
export function list2string(li) { 
    if (!li) { 
        return null;
    }
    return li.join(",");
}



export const protocols = [
    {
        value: 'IPv4',
        label: 'IPv4'
    },
    {
        value:'IPv6',
        label:'IPv6'
    }
]

export const tableHeaders = [ 
    "IP", "Country", "City","Region","ISP","Usage Type","Time Zone"
]

export const sources = [
    "IP2Location","Greynoise"
]
//reverse lookup 
export const ipConstants = {
    countryName : "",
    usageType:""
}

export const pageSizeList = [ 
    {
        "label" : "5", 
        "value" : 5
    },    
    {
        "label" : "10", 
        "value" : 10
    },    
    {
        "label" : "20", 
        "value" : 20
    },    
    {
        "label" : "50", 
        "value" : 50
    }
]
// all possible constants for each field 
export const usage_type = [
    {
        "label":" ", 
        "value":""
    },
    {
        "label" : "Commercial", 
        "value" : "COM" 
    },{
        "label" : "Organization", 
        "value" : "ORG" 
    },{
        "label" : "Government", 
        "value" : "GOV" 
    },{
        "label" : "Military", 
        "value" : "MIL" 
    },{
        "label" : "Education", 
        "value" : "EDU"
    },{
        "label" : "Library", 
        "value" : "LIB" 
    },{
        "label" : "Content Delivery Network", 
        "value" : "CDN" 
    },{
        "label" : "Fixed Line ISP", 
        "value" : "ISP" 
    },{
        "label" : "Mobile ISP", 
        "value" : "MOB" 
    },{
        "label" : "Data Center", 
        "value" : "DCH" 
    },{
        "label" : "Search Engine SPider", 
        "value" : "SES" 
    },{
        "label" : "Reserved", 
        "value" : "RSV" 
    },
]
export const countries = [
    {
        "label":"   ", 
        "value":""
    },
    {
        "label": "Andorra",
        "value": "Andorra"
    },
    {
        "label": "United Arab Emirates",
        "value": "United Arab Emirates"
    },
    {
        "label": "Afghanistan",
        "value": "Afghanistan"
    },
    {
        "label": "Antigua & Barbuda",
        "value": "Antigua & Barbuda"
    },
    {
        "label": "Anguilla",
        "value": "Anguilla"
    },
    {
        "label": "Albania",
        "value": "Albania"
    },
    {
        "label": "Armenia",
        "value": "Armenia"
    },
    {
        "label": "Angola",
        "value": "Angola"
    },
    {
        "label": "Antarctica",
        "value": "Antarctica"
    },
    {
        "label": "Argentina",
        "value": "Argentina"
    },
    {
        "label": "American Samoa",
        "value": "American Samoa"
    },
    {
        "label": "Austria",
        "value": "Austria"
    },
    {
        "label": "Australia",
        "value": "Australia"
    },
    {
        "label": "Aruba",
        "value": "Aruba"
    },
    {
        "label": "Åland Islands",
        "value": "Åland Islands"
    },
    {
        "label": "Azerbaijan",
        "value": "Azerbaijan"
    },
    {
        "label": "Bosnia & Herzegovina",
        "value": "Bosnia & Herzegovina"
    },
    {
        "label": "Barbados",
        "value": "Barbados"
    },
    {
        "label": "Bangladesh",
        "value": "Bangladesh"
    },
    {
        "label": "Belgium",
        "value": "Belgium"
    },
    {
        "label": "Burkina Faso",
        "value": "Burkina Faso"
    },
    {
        "label": "Bulgaria",
        "value": "Bulgaria"
    },
    {
        "label": "Bahrain",
        "value": "Bahrain"
    },
    {
        "label": "Burundi",
        "value": "Burundi"
    },
    {
        "label": "Benin",
        "value": "Benin"
    },
    {
        "label": "St Barthélemy",
        "value": "St Barthélemy"
    },
    {
        "label": "Bermuda",
        "value": "Bermuda"
    },
    {
        "label": "Brunei",
        "value": "Brunei"
    },
    {
        "label": "Bolivia",
        "value": "Bolivia"
    },
    {
        "label": "Caribbean Netherlands",
        "value": "Caribbean Netherlands"
    },
    {
        "label": "Brazil",
        "value": "Brazil"
    },
    {
        "label": "Bahamas",
        "value": "Bahamas"
    },
    {
        "label": "Bhutan",
        "value": "Bhutan"
    },
    {
        "label": "Bouvet Island",
        "value": "Bouvet Island"
    },
    {
        "label": "Botswana",
        "value": "Botswana"
    },
    {
        "label": "Belarus",
        "value": "Belarus"
    },
    {
        "label": "Belize",
        "value": "Belize"
    },
    {
        "label": "Canada",
        "value": "Canada"
    },
    {
        "label": "Cocos (Keeling) Islands",
        "value": "Cocos (Keeling) Islands"
    },
    {
        "label": "Congo - Kinshasa",
        "value": "Congo - Kinshasa"
    },
    {
        "label": "Central African Republic",
        "value": "Central African Republic"
    },
    {
        "label": "Congo - Brazzaville",
        "value": "Congo - Brazzaville"
    },
    {
        "label": "Switzerland",
        "value": "Switzerland"
    },
    {
        "label": "Côte d’Ivoire",
        "value": "Côte d’Ivoire"
    },
    {
        "label": "Cook Islands",
        "value": "Cook Islands"
    },
    {
        "label": "Chile",
        "value": "Chile"
    },
    {
        "label": "Cameroon",
        "value": "Cameroon"
    },
    {
        "label": "China",
        "value": "China"
    },
    {
        "label": "Colombia",
        "value": "Colombia"
    },
    {
        "label": "Costa Rica",
        "value": "Costa Rica"
    },
    {
        "label": "Cuba",
        "value": "Cuba"
    },
    {
        "label": "Cape Verde",
        "value": "Cape Verde"
    },
    {
        "label": "Curaçao",
        "value": "Curaçao"
    },
    {
        "label": "Christmas Island",
        "value": "Christmas Island"
    },
    {
        "label": "Cyprus",
        "value": "Cyprus"
    },
    {
        "label": "Czechia",
        "value": "Czechia"
    },
    {
        "label": "Germany",
        "value": "Germany"
    },
    {
        "label": "Djibouti",
        "value": "Djibouti"
    },
    {
        "label": "Denmark",
        "value": "Denmark"
    },
    {
        "label": "Dominica",
        "value": "Dominica"
    },
    {
        "label": "Dominican Republic",
        "value": "Dominican Republic"
    },
    {
        "label": "Algeria",
        "value": "Algeria"
    },
    {
        "label": "Ecuador",
        "value": "Ecuador"
    },
    {
        "label": "Estonia",
        "value": "Estonia"
    },
    {
        "label": "Egypt",
        "value": "Egypt"
    },
    {
        "label": "Western Sahara",
        "value": "Western Sahara"
    },
    {
        "label": "Eritrea",
        "value": "Eritrea"
    },
    {
        "label": "Spain",
        "value": "Spain"
    },
    {
        "label": "Ethiopia",
        "value": "Ethiopia"
    },
    {
        "label": "Finland",
        "value": "Finland"
    },
    {
        "label": "Fiji",
        "value": "Fiji"
    },
    {
        "label": "Falkland Islands",
        "value": "Falkland Islands"
    },
    {
        "label": "Micronesia",
        "value": "Micronesia"
    },
    {
        "label": "Faroe Islands",
        "value": "Faroe Islands"
    },
    {
        "label": "France",
        "value": "France"
    },
    {
        "label": "Gabon",
        "value": "Gabon"
    },
    {
        "label": "United Kingdom",
        "value": "United Kingdom"
    },
    {
        "label": "Grenada",
        "value": "Grenada"
    },
    {
        "label": "Georgia",
        "value": "Georgia"
    },
    {
        "label": "French Guiana",
        "value": "French Guiana"
    },
    {
        "label": "Guernsey",
        "value": "Guernsey"
    },
    {
        "label": "Ghana",
        "value": "Ghana"
    },
    {
        "label": "Gibraltar",
        "value": "Gibraltar"
    },
    {
        "label": "Greenland",
        "value": "Greenland"
    },
    {
        "label": "Gambia",
        "value": "Gambia"
    },
    {
        "label": "Guinea",
        "value": "Guinea"
    },
    {
        "label": "Guadeloupe",
        "value": "Guadeloupe"
    },
    {
        "label": "Equatorial Guinea",
        "value": "Equatorial Guinea"
    },
    {
        "label": "Greece",
        "value": "Greece"
    },
    {
        "label": "South Georgia & South Sandwich Islands",
        "value": "South Georgia & South Sandwich Islands"
    },
    {
        "label": "Guatemala",
        "value": "Guatemala"
    },
    {
        "label": "Guam",
        "value": "Guam"
    },
    {
        "label": "Guinea-Bissau",
        "value": "Guinea-Bissau"
    },
    {
        "label": "Guyana",
        "value": "Guyana"
    },
    {
        "label": "Hong Kong SAR China",
        "value": "Hong Kong SAR China"
    },
    {
        "label": "Heard & McDonald Islands",
        "value": "Heard & McDonald Islands"
    },
    {
        "label": "Honduras",
        "value": "Honduras"
    },
    {
        "label": "Croatia",
        "value": "Croatia"
    },
    {
        "label": "Haiti",
        "value": "Haiti"
    },
    {
        "label": "Hungary",
        "value": "Hungary"
    },
    {
        "label": "Indonesia",
        "value": "Indonesia"
    },
    {
        "label": "Ireland",
        "value": "Ireland"
    },
    {
        "label": "Israel",
        "value": "Israel"
    },
    {
        "label": "Isle of Man",
        "value": "Isle of Man"
    },
    {
        "label": "India",
        "value": "India"
    },
    {
        "label": "British Indian Ocean Territory",
        "value": "British Indian Ocean Territory"
    },
    {
        "label": "Iraq",
        "value": "Iraq"
    },
    {
        "label": "Iran",
        "value": "Iran"
    },
    {
        "label": "Iceland",
        "value": "Iceland"
    },
    {
        "label": "Italy",
        "value": "Italy"
    },
    {
        "label": "Jersey",
        "value": "Jersey"
    },
    {
        "label": "Jamaica",
        "value": "Jamaica"
    },
    {
        "label": "Jordan",
        "value": "Jordan"
    },
    {
        "label": "Japan",
        "value": "Japan"
    },
    {
        "label": "Kenya",
        "value": "Kenya"
    },
    {
        "label": "Kyrgyzstan",
        "value": "Kyrgyzstan"
    },
    {
        "label": "Cambodia",
        "value": "Cambodia"
    },
    {
        "label": "Kiribati",
        "value": "Kiribati"
    },
    {
        "label": "Comoros",
        "value": "Comoros"
    },
    {
        "label": "St Kitts & Nevis",
        "value": "St Kitts & Nevis"
    },
    {
        "label": "North Korea",
        "value": "North Korea"
    },
    {
        "label": "South Korea",
        "value": "South Korea"
    },
    {
        "label": "Kuwait",
        "value": "Kuwait"
    },
    {
        "label": "Cayman Islands",
        "value": "Cayman Islands"
    },
    {
        "label": "Kazakhstan",
        "value": "Kazakhstan"
    },
    {
        "label": "Laos",
        "value": "Laos"
    },
    {
        "label": "Lebanon",
        "value": "Lebanon"
    },
    {
        "label": "St Lucia",
        "value": "St Lucia"
    },
    {
        "label": "Liechtenstein",
        "value": "Liechtenstein"
    },
    {
        "label": "Sri Lanka",
        "value": "Sri Lanka"
    },
    {
        "label": "Liberia",
        "value": "Liberia"
    },
    {
        "label": "Lesotho",
        "value": "Lesotho"
    },
    {
        "label": "Lithuania",
        "value": "Lithuania"
    },
    {
        "label": "Luxembourg",
        "value": "Luxembourg"
    },
    {
        "label": "Latvia",
        "value": "Latvia"
    },
    {
        "label": "Libya",
        "value": "Libya"
    },
    {
        "label": "Morocco",
        "value": "Morocco"
    },
    {
        "label": "Monaco",
        "value": "Monaco"
    },
    {
        "label": "Moldova",
        "value": "Moldova"
    },
    {
        "label": "Montenegro",
        "value": "Montenegro"
    },
    {
        "label": "St Martin",
        "value": "St Martin"
    },
    {
        "label": "Madagascar",
        "value": "Madagascar"
    },
    {
        "label": "Marshall Islands",
        "value": "Marshall Islands"
    },
    {
        "label": "North Macedonia",
        "value": "North Macedonia"
    },
    {
        "label": "Mali",
        "value": "Mali"
    },
    {
        "label": "Myanmar (Burma)",
        "value": "Myanmar (Burma)"
    },
    {
        "label": "Mongolia",
        "value": "Mongolia"
    },
    {
        "label": "Macao SAR China",
        "value": "Macao SAR China"
    },
    {
        "label": "Northern Mariana Islands",
        "value": "Northern Mariana Islands"
    },
    {
        "label": "Martinique",
        "value": "Martinique"
    },
    {
        "label": "Mauritania",
        "value": "Mauritania"
    },
    {
        "label": "Montserrat",
        "value": "Montserrat"
    },
    {
        "label": "Malta",
        "value": "Malta"
    },
    {
        "label": "Mauritius",
        "value": "Mauritius"
    },
    {
        "label": "Maldives",
        "value": "Maldives"
    },
    {
        "label": "Malawi",
        "value": "Malawi"
    },
    {
        "label": "Mexico",
        "value": "Mexico"
    },
    {
        "label": "Malaysia",
        "value": "Malaysia"
    },
    {
        "label": "Mozambique",
        "value": "Mozambique"
    },
    {
        "label": "Namibia",
        "value": "Namibia"
    },
    {
        "label": "New Caledonia",
        "value": "New Caledonia"
    },
    {
        "label": "Niger",
        "value": "Niger"
    },
    {
        "label": "Norfolk Island",
        "value": "Norfolk Island"
    },
    {
        "label": "Nigeria",
        "value": "Nigeria"
    },
    {
        "label": "Nicaragua",
        "value": "Nicaragua"
    },
    {
        "label": "Netherlands",
        "value": "Netherlands"
    },
    {
        "label": "Norway",
        "value": "Norway"
    },
    {
        "label": "Nepal",
        "value": "Nepal"
    },
    {
        "label": "Nauru",
        "value": "Nauru"
    },
    {
        "label": "Niue",
        "value": "Niue"
    },
    {
        "label": "New Zealand",
        "value": "New Zealand"
    },
    {
        "label": "Oman",
        "value": "Oman"
    },
    {
        "label": "Panama",
        "value": "Panama"
    },
    {
        "label": "Peru",
        "value": "Peru"
    },
    {
        "label": "French Polynesia",
        "value": "French Polynesia"
    },
    {
        "label": "Papua New Guinea",
        "value": "Papua New Guinea"
    },
    {
        "label": "Philippines",
        "value": "Philippines"
    },
    {
        "label": "Pakistan",
        "value": "Pakistan"
    },
    {
        "label": "Poland",
        "value": "Poland"
    },
    {
        "label": "St Pierre & Miquelon",
        "value": "St Pierre & Miquelon"
    },
    {
        "label": "Pitcairn Islands",
        "value": "Pitcairn Islands"
    },
    {
        "label": "Puerto Rico",
        "value": "Puerto Rico"
    },
    {
        "label": "Palestinian Territories",
        "value": "Palestinian Territories"
    },
    {
        "label": "Portugal",
        "value": "Portugal"
    },
    {
        "label": "Palau",
        "value": "Palau"
    },
    {
        "label": "Paraguay",
        "value": "Paraguay"
    },
    {
        "label": "Qatar",
        "value": "Qatar"
    },
    {
        "label": "Réunion",
        "value": "Réunion"
    },
    {
        "label": "Romania",
        "value": "Romania"
    },
    {
        "label": "Serbia",
        "value": "Serbia"
    },
    {
        "label": "Russia",
        "value": "Russia"
    },
    {
        "label": "Rwanda",
        "value": "Rwanda"
    },
    {
        "label": "Saudi Arabia",
        "value": "Saudi Arabia"
    },
    {
        "label": "Solomon Islands",
        "value": "Solomon Islands"
    },
    {
        "label": "Seychelles",
        "value": "Seychelles"
    },
    {
        "label": "Sudan",
        "value": "Sudan"
    },
    {
        "label": "Sweden",
        "value": "Sweden"
    },
    {
        "label": "Singapore",
        "value": "Singapore"
    },
    {
        "label": "St Helena",
        "value": "St Helena"
    },
    {
        "label": "Slovenia",
        "value": "Slovenia"
    },
    {
        "label": "Svalbard & Jan Mayen",
        "value": "Svalbard & Jan Mayen"
    },
    {
        "label": "Slovakia",
        "value": "Slovakia"
    },
    {
        "label": "Sierra Leone",
        "value": "Sierra Leone"
    },
    {
        "label": "San Marino",
        "value": "San Marino"
    },
    {
        "label": "Senegal",
        "value": "Senegal"
    },
    {
        "label": "Somalia",
        "value": "Somalia"
    },
    {
        "label": "Suriname",
        "value": "Suriname"
    },
    {
        "label": "South Sudan",
        "value": "South Sudan"
    },
    {
        "label": "São Tomé & Príncipe",
        "value": "São Tomé & Príncipe"
    },
    {
        "label": "El Salvador",
        "value": "El Salvador"
    },
    {
        "label": "Sint Maarten",
        "value": "Sint Maarten"
    },
    {
        "label": "Syria",
        "value": "Syria"
    },
    {
        "label": "Eswatini",
        "value": "Eswatini"
    },
    {
        "label": "Turks & Caicos Islands",
        "value": "Turks & Caicos Islands"
    },
    {
        "label": "Chad",
        "value": "Chad"
    },
    {
        "label": "French Southern Territories",
        "value": "French Southern Territories"
    },
    {
        "label": "Togo",
        "value": "Togo"
    },
    {
        "label": "Thailand",
        "value": "Thailand"
    },
    {
        "label": "Tajikistan",
        "value": "Tajikistan"
    },
    {
        "label": "Tokelau",
        "value": "Tokelau"
    },
    {
        "label": "Timor-Leste",
        "value": "Timor-Leste"
    },
    {
        "label": "Turkmenistan",
        "value": "Turkmenistan"
    },
    {
        "label": "Tunisia",
        "value": "Tunisia"
    },
    {
        "label": "Tonga",
        "value": "Tonga"
    },
    {
        "label": "Turkey",
        "value": "Turkey"
    },
    {
        "label": "Trinidad & Tobago",
        "value": "Trinidad & Tobago"
    },
    {
        "label": "Tuvalu",
        "value": "Tuvalu"
    },
    {
        "label": "Taiwan",
        "value": "Taiwan"
    },
    {
        "label": "Tanzania",
        "value": "Tanzania"
    },
    {
        "label": "Ukraine",
        "value": "Ukraine"
    },
    {
        "label": "Uganda",
        "value": "Uganda"
    },
    {
        "label": "US Outlying Islands",
        "value": "US Outlying Islands"
    },
    {
        "label": "United States of America",
        "value": "United States of America"
    },
    {
        "label": "Uruguay",
        "value": "Uruguay"
    },
    {
        "label": "Uzbekistan",
        "value": "Uzbekistan"
    },
    {
        "label": "Vatican City",
        "value": "Vatican City"
    },
    {
        "label": "St Vincent & the Grenadines",
        "value": "St Vincent & the Grenadines"
    },
    {
        "label": "Venezuela",
        "value": "Venezuela"
    },
    {
        "label": "British Virgin Islands",
        "value": "British Virgin Islands"
    },
    {
        "label": "US Virgin Islands",
        "value": "US Virgin Islands"
    },
    {
        "label": "Vietnam",
        "value": "Vietnam"
    },
    {
        "label": "Vanuatu",
        "value": "Vanuatu"
    },
    {
        "label": "Wallis & Futuna",
        "value": "Wallis & Futuna"
    },
    {
        "label": "Samoa",
        "value": "Samoa"
    },
    {
        "label": "Yemen",
        "value": "Yemen"
    },
    {
        "label": "Mayotte",
        "value": "Mayotte"
    },
    {
        "label": "South Africa",
        "value": "South Africa"
    },
    {
        "label": "Zambia",
        "value": "Zambia"
    },
    {
        "label": "Zimbabwe",
        "value": "Zimbabwe"
    }
]