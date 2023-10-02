import axios from "./axios"
import handleLogout from "./handleLogout"

async function refresh(url, data) {
    const refresh_token = sessionStorage.getItem('refresh_token')

    console.log(`Sending token refresh request ${refresh_token}`)

    try{
        const res = await axios.post('/refresh-token', "", {
                headers: {
                    "Content-Type": 'application/json',
                    Authorization: `Bearer ${refresh_token}`
                }
            } 
        )
    

        // console.log(res)
        if(res !== null && res !== undefined){
            sessionStorage.setItem('access_token', res.data.access_token)
            sendRequestWithToken(url, data)
        } else {
            handleLogout()
        }
    } catch(err){
        handleLogout()
    }
   
   
    
}

async function sendRequest(method, url, data) {
    try {
        const access_token = sessionStorage.getItem('access_token')    

        // console.log(`Sending request with token ${access_token}`)

        // const res = await axios.post(url, data, {
        //     headers: {
        //         "Content-Type": 'application/json',
        //         Authorization: `Bearer ${access_token}`
        //     }
            
                
        // })

        const res = await axios({
            method: method,
            url: url,
            data: data,
            headers: {
                "Content-Type": 'application/json',
                Authorization: `Bearer ${access_token}`
            }
        })

        // console.log(res)

        if(res.status === 401){
           refresh(url, data)

        }  else {
            return res
        }  
        
       

    } catch (error) {
        refresh(url, data)
    }
}




export default sendRequest