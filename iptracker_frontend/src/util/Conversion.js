export const convertIPv4=(ipv4)=> { 
    const array = ipv4.split(".");
    const ipNumber = 16777216*array[0] + 65536*array[1] + 256*array[2] + array[3];
    return ipNumber
}

//most ipv6 are in hexadecimals. convert to decimal for each part first 
export const convertIPv6 = (ipv6) => { 
    const array = ipv6.split(":"); 
    const newArray = array.map( item => parseInt(item,16))
    console.log("Converted ipv6 to decimal:" + newArray);
    const ipNumber = (65536^7)*newArray[0] + (65536^6)*newArray[1] + (65536^5)*newArray[2] + (65536^4)*newArray[3] + (65536^3)*newArray[4] + (65536^2)*newArray[5] + 65536*newArray[6] + newArray[7]
    return ipNumber
}

// function convertHexArray(item,index,arr) { 
//     arr[index] = parseInt(item,16);
// }

