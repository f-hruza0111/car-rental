
import handleSubmit from '../api/handleSubmit';
function CustomerRegistration(){

    const url = "/customer/registration";

    function handler(e){
        handleSubmit(e, url)
        window.location.href='/login'
    }

    return (
        <div>
            
            <div className='container'>
           
                <form onSubmit={(e) => handler(e)}>
                    <div class="form-group">
                        <label for="email">Email address</label>
                        <input type="email" class="form-control" id="email" name='email'/>

                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name='password'/>

                        <label for="firstName">First name</label>
                        <input type="text" class="form-control" id="firstName" name='firstName'/>

                        <label for="lastName">Last name</label>
                        <input type="text" class="form-control" id="lastName" name='lastName'/>

                        <label for="address">Address</label>
                        <input type="text" class="form-control" id="address" name='address'/>
                        <button className="btn btn-primary" type="submit">Submit</button>
                    </div>
                </form> 
            </div>
        </div>
       
        
    )
}

export default CustomerRegistration;