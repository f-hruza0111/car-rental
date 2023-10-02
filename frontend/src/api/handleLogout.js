import axios from "../api/axios";


async function handleLogout() {
    const access_token = sessionStorage.getItem('access_token')

    console.log(access_token)

    const res = await axios.post("/logout", "",{
        headers: {
          Authorization : `Bearer ${access_token}`
        }
      });

    console.log(res)

    sessionStorage.clear()


    window.location.href = '/'
}

export default handleLogout