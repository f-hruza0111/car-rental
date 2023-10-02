import axios from '../api/axios'
import { useState, useEffect } from 'react'
import AdGallery from './AdGallery'
import Pagination from './Pagination'
import Navbar from './Navbar'

const Home = () => {

    const [ads, setAds] = useState([]);
    const [totalPages, setTotalPages] = useState();
    const [page, setPage] = useState(0)
    const [numberOfPages, setNumberOfPages] = useState(1)
    const [adsPerPage, setAdsPerPage] = useState(9)
  
    const fetchAds = async () => {
      const res = await axios.get("/ads?page=" + page + "&size=" + adsPerPage)
  
      //  console.log(res);
      setAds(res.data.content);
      setTotalPages(res.data.totalPages);
      
  
      //  console.log(totalPages);
      //  console.log(res.data.totalPages)
      //  console.log(res.data.totalPages)
  
      //  console.log(res);
  
      
    }
  
  
    useEffect(() => {
       fetchAds()
    }, [page]);
  
    const paginate = (pageNumber) => {
      if(page != pageNumber) setPage(pageNumber);
    }
    // console.log(totalPages)
  
    return (
      <div>
        <Navbar/>
        <AdGallery ads = {ads}/>
        <Pagination adsPerPage = {adsPerPage} totalPages = {totalPages} paginate = {paginate}/>
      </div>
      
    )
}

export default Home