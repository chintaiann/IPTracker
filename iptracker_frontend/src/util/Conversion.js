export const convertToIPv4=(ipNumber) =>{ 
    let  w = Math.floor(ipNumber / 16777216) % 256
    let  x = Math.floor( ipNumber  / 65536) % 256
    let y = Math.floor( ipNumber  / 256) % 256
    let z = Math.floor(ipNumber) % 256

    return w+"."+x+"."+y+"."+z;
};

