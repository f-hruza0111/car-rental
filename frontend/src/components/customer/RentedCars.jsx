import { useState, useEffect } from "react";
import Navbar from "../Navbar"
import { Link } from "react-router-dom";
import sendRequst from "../../api/protectedHttpRequsts"

function RentedCars(){


    const user_id = sessionStorage.getItem('user_id')
    const url = `/customer/${user_id}/cars`


    const [cars, setCars] = useState([]);  

    const fetchRentedCars = async () => {
        const res = await sendRequst('get', url, "");
    
        //  console.log(res);
        setCars(res.data);
        // console.log(receipts);
        
        
    
        //  console.log(totalPages);
        //  console.log(res.data.totalPages)
        //  console.log(res.data.totalPages)
    
        //  console.log(res);
    
        
    }
    
    
      useEffect(() => {
        fetchRentedCars()
      }, []);
    

    
    return(
        <>
        
            <table class="table table-dark">
                <thead>
                
                         <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Make</th>
                            <th scope="col">Model</th>
                            <th scope="col">Licese plate</th>
                            <th scope="col">Year of production</th>
                            <th scope="col">Kilometers</th>
                            <th scope="col">Rental company</th>
                            <th></th>
                        </tr>
                    
                    
                </thead>
                <tbody>
                        {
                            cars.map(car => (
                                <tr key={car.id}>
                                    <th scope="row"><Link to='/car/details'>{car.id}</Link></th>
                                    <td>{car.make}</td>
                                    <td>{car.model}</td>
                                    <td>{car.licensePlate}</td>                                                                   
                                    <td>{car.yearOfProduction}</td>
                                    <td>{car.kilometers}</td>
                                    <td><Link to='/'>{car.rentalCompany}</Link></td>
                                    <td><button type="button" class="btn btn-danger">Cancel rental</button></td>
                                </tr>
                            ))
                        }
                </tbody>
            </table>
        </>

    )
}


export default RentedCars