import { useState, useEffect } from "react";
import Navbar from "./Navbar";
import { Link } from "react-router-dom";
import sendRequst from "../api/protectedHttpRequsts"


function Receipts(){

    const user_id = sessionStorage.getItem('user_id')
    const role = sessionStorage.getItem('role')
    const url = `/customer/${user_id}/receipts`

    const [receipts, setReceipts] = useState([]);

    let table = <Navbar/>;

    const fetchReceipts = async () => {
        const res = await sendRequst('get', url, "");
    
        //  console.log(res);
        setReceipts(res.data);
        // console.log(receipts);
        
        
    
        //  console.log(totalPages);
        //  console.log(res.data.totalPages)
        //  console.log(res.data.totalPages)
    
        //  console.log(res);
    
        
    }
    
    
      useEffect(() => {
        fetchReceipts()
      }, []);


    return (<>
   
    <table class="table table-dark">
        <thead>
            {
                <tr>
                <th scope="col">ID</th>
                <th scope="col">Car</th>
                <th scope="col">Licence plate</th> 
                {role === 'CUSTOMER' ? <th scope="col">Rental company</th> :<th scope="col">Customer</th>}
                  
                <th scope="col">Date of rental</th>
                <th scope="col">Days rented for</th>
                <th scope="col">Return date</th>
                <th scope="col">Cost per day</th>
                <th scope="col">Total const</th>
                </tr>
            }
            
        </thead>
        <tbody>
                {
                    receipts.map(receipt => (
                        <tr key={receipt.id}>
                            <th scope="row">{receipt.id}</th>
                            <td><Link to='/'>{receipt.car}</Link></td>
                            <td>{receipt.licensePlate}</td>
                            
                            {role ==='CUSTOMER' 
                                ? <td><Link to='/'>{receipt.rentalCompany}</Link></td>
                                : null
                            }

                            {role ==='RENTAL_COMPANY' 
                                ? <td><Link to='/'>{receipt.customer}</Link></td>
                                : null
                            }
                                
                             

                            
                            <td>{receipt.rentedAt}</td>
                            <td>{receipt.rentedFor}</td>
                            <td>{receipt.returnDate}</td>
                            <td>{receipt.costPerDay}</td>
                            <td>{receipt.totalCost}</td>
                        </tr>
                    ))
                }
        </tbody>
    </table>
</>);
}


export default Receipts