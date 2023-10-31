import axios from '../api/axios'
import { useState, useEffect } from 'react'
import AdGallery from './AdGallery'
import Pagination from './Pagination'
import { useParams } from 'react-router-dom'

const Ads = ({url, pageProp, adsPerPageProp}) => {

    const [ads, setAds] = useState([]);
    const [totalPages, setTotalPages] = useState();
    const [page, setPage] = useState(pageProp)
    const [numberOfPages, setNumberOfPages] = useState(1)
    const [adsPerPage, setAdsPerPage] = useState(adsPerPageProp)

    const baseUrl = 'ads?page=' + page + "&size=" + adsPerPage
    const {id} = useParams()
    // console.log(id)

    let finalUrl = baseUrl
    if(url !== undefined) {
      finalUrl = url + id + '/' + baseUrl
    }
    
    // console.log(urlConcat)
    const fetchAds = async (url) => {
      const res = await axios.get(url)
  
      //  console.log(res);
      setAds(res.data.content);
      setTotalPages(res.data.totalPages);
      // console.log(ads)
  
      //  console.log(totalPages);
      //  console.log(res.data.totalPages)
      //  console.log(res.data.totalPages)
  
      //  console.log(res);
  
      
    }
  
  
    useEffect(() => {
       fetchAds(finalUrl)
    }, [page]);
  
    const paginate = (pageNumber) => {
      if(page != pageNumber) setPage(pageNumber);
    }
    // console.log(totalPages)
  
    return (
      <div>
       
        <Pagination adsPerPage = {adsPerPage} totalPages = {totalPages} paginate = {paginate}/>
        <AdGallery ads = {ads}/>
      </div>
      
    )
}

export default Ads