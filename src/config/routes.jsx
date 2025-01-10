
import {Routes,Route} from 'react-router'
import App from '../App' 
import ChatPage from '../Components/ChatPage'

const AppRoutes = () => {
  return (
    <Routes>
        <Route path="/" element={<App />} />
        <Route path="/chat" element={<ChatPage/>} />
    </Routes>
    
  )
}

export default AppRoutes
