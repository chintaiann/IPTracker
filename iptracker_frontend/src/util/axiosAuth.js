import axios from "axios"
const axiosAuth = axios.create()

//attach our keycloak token to every request made by axiosAuth
axiosAuth.interceptors.request.use(
    config => { 
        const token = window.accessToken ? window.accessToken : "notoken";
        config.headers['Authorization'] = 'Bearer ' + token; 
        return config;
    },
    error => { 
       return  Promise.reject(error); 
    }
)

axiosAuth.interceptors.response.use( 
    (response) => { 
        return response
    }, function (error) { 
        if (error.response.status === 401) { 
            console.log("auth error")
        }
        return Promise.reject(error);
    }
)

export default axiosAuth
