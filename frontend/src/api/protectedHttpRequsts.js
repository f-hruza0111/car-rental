
import axios from "./axios"
import handleLogout from "./handleLogout"

async function refresh(method, url, data) {
    const refresh_token = sessionStorage.getItem('refresh_token')

    console.log(`Sending token refresh request ${refresh_token}`)

    try{
        const res = await axios({
                method: 'post',
                url: '/refresh-token',
                headers: {
                    "Content-Type": 'application/json',
                    Authorization: `Bearer ${refresh_token}`
                }
            }
        )
    

        console.log('Token refresh response \n')
        console.log(res)
        if(res !== null && res !== undefined && res.data.access_token !==undefined){
            sessionStorage.setItem('access_token', res.data.access_token)
           return await sendRequest(method, url, data)
        } else {
            console.log("response wrong")
            // handleLogout()
        }
    } catch(err){
        console.log(err)
        console.log('Caught error in refresh token')
        handleLogout()
    }
    
}

async function sendRequest(method, url, data) {

   
    try {
        const access_token = sessionStorage.getItem('access_token')    

        const res = await axios({
            method: method,
            url: url,
            data: data,
            headers: {
                "Content-Type": 'application/json',
                Authorization: `Bearer ${access_token}`
            }
        })

        console.log('Request response: \n')
        console.log(res) 

        return res

    } catch (error) {
        console.log(`Caught error sending request ${error}`)
        return await refresh(method, url, data)
    }
}





export default sendRequest