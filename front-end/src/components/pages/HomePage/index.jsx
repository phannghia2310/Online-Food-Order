import classNames from 'classnames/bind';
import styles from './HomePage.module.scss';
import img_bgr from '../../../food_images/Home-bg.png';

const cln = classNames.bind(styles);

HomePage.propTypes = {};
function HomePage(props) {
   return (
      <div className={cln('wrapper')}>
         <div className={cln('container')}>
            <div className={cln('title')}>
               <p>
                  <span>Food </span> <span style={{ color: 'black' }}>&</span>
                  <span style={{ color: 'orange' }}> Online Ordering</span>
               </p>
            </div>
            <div className={cln('contents')}>
               <div className={cln('contents-bgr')}>
                  <img src={img_bgr} alt="" />
               </div>
            </div>
         </div>
      </div>
   );
}

export default HomePage;
