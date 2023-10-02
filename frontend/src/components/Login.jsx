import Navbar from "./Navbar"
import handleSubmit from '../api/handleSubmit';
import { redirect } from "react-router-dom"

function Login(){
    const url = "/login"

   async function handleLogin(e){
        const res = await handleSubmit(e, url)
        console.log(res.data)

        sessionStorage.setItem("access_token", res.data.access_token)
        sessionStorage.setItem("refresh_token", res.data.refresh_token)
        sessionStorage.setItem("user_id", res.data.user_id)
        sessionStorage.setItem("role", res.data.role)

        window.location.href = '/'
    }

    return(
        <div>
            <Navbar/>
            <div className='container'>   
                <form onSubmit={(e) => handleLogin(e)}>
                    <div class="form-group">
                        <label for="email">Email address</label>
                        <input type="email" class="form-control" id="email" name='email'/>

                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name='password'/>

                        <button className="btn btn-primary" type="submit">Log in</button>
                    </div>
                </form> 
            </div>
        </div>
    )
}

export default Login