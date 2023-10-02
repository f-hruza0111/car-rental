
import Navbar from "./Navbar"
import handleSubmit from '../api/handleSubmit';

function RentalCompanyRegistration(){
    const url = "/rental-company/registration"
    
    function handler(e){
        handleSubmit(e, url)
        window.location.href='/login'
    }

    return (
        <div>
            <Navbar></Navbar>
            <div className="container">
                <form onSubmit={(e) => handler(e)}>
                    <div className="form-group">
                        <label htmlFor="email">Email address</label>
                        <input type="email" className="form-control" /*value={data.email}*/ id="email" name='email'/>

                        <label htmlFor="password">Password</label>
                        <input type="password" className="form-control" /*value={data.password}*/ id="password" name='password'/>

                        <label htmlFor="name">Company name</label>
                        <input type="text" className="form-control" /*value={data.name}*/ id="name" name='name'/>

                        <label htmlFor="phoneNumber">Phone number</label>
                        <input type="text" className="form-control" /*value={data.phoneNumber}*/ id="phoneNumber" name='phoneNumber'/>

                        <label htmlFor="address">Address</label>
                        <input type="text" className="form-control" /*value={data.address}*/ id="address" name='address'/>
                        <button className="btn btn-primary" type="submit">Submit</button>
                    </div>
                </form>        
            </div>
        </div>
        
    )
}

function submit(){

}

export default RentalCompanyRegistration