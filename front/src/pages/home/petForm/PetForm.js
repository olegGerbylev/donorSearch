import React from 'react';
import classes from './petForm.module.scss'

const PetForm = () => {
    return (
        <div className={classes}>
            <div>
                {false ?
                    <div className={classes.photoContainer}>
                        <img/>
                    </div>
                    :
                    <div className={classes.photoContainer}>
                        <div className={classes.defaultPhoto}>
                            <svg width="80" height="80" viewBox="0 0 68 60" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M60.25 8.03125H50.8033L46.1406 1.03235C45.9605 0.762422 45.7165 0.541196 45.4303 0.388348C45.1441 0.235501 44.8245 0.155771 44.5 0.156252H23.5C23.1755 0.155771 22.8559 0.235501 22.5697 0.388348C22.2835 0.541196 22.0395 0.762422 21.8594 1.03235L17.1934 8.03125H7.75C5.83547 8.03125 3.99935 8.7918 2.64557 10.1456C1.29179 11.4994 0.53125 13.3355 0.53125 15.25V52C0.53125 53.9145 1.29179 55.7506 2.64557 57.1044C3.99935 58.4582 5.83547 59.2188 7.75 59.2188H60.25C62.1645 59.2188 64.0006 58.4582 65.3544 57.1044C66.7082 55.7506 67.4688 53.9145 67.4688 52V15.25C67.4688 13.3355 66.7082 11.4994 65.3544 10.1456C64.0006 8.7918 62.1645 8.03125 60.25 8.03125ZM63.5312 52C63.5312 52.8702 63.1856 53.7048 62.5702 54.3202C61.9548 54.9356 61.1202 55.2812 60.25 55.2812H7.75C6.87976 55.2812 6.04516 54.9356 5.42981 54.3202C4.81445 53.7048 4.46875 52.8702 4.46875 52V15.25C4.46875 14.3798 4.81445 13.5452 5.42981 12.9298C6.04516 12.3145 6.87976 11.9688 7.75 11.9688H18.25C18.5745 11.9692 18.8941 11.8895 19.1803 11.7367C19.4665 11.5838 19.7105 11.3626 19.8906 11.0927L24.5533 4.09375H43.4434L48.1094 11.0927C48.2895 11.3626 48.5335 11.5838 48.8197 11.7367C49.1059 11.8895 49.4255 11.9692 49.75 11.9688H60.25C61.1202 11.9688 61.9548 12.3145 62.5702 12.9298C63.1856 13.5452 63.5312 14.3798 63.5312 15.25V52ZM34 18.5313C31.2743 18.5313 28.6099 19.3395 26.3435 20.8538C24.0772 22.3681 22.3109 24.5205 21.2678 27.0386C20.2247 29.5568 19.9518 32.3278 20.4836 35.0011C21.0153 37.6744 22.3278 40.13 24.2552 42.0573C26.1825 43.9847 28.6381 45.2972 31.3114 45.8289C33.9847 46.3607 36.7557 46.0878 39.2739 45.0447C41.7921 44.0016 43.9444 42.2353 45.4587 39.969C46.973 37.7026 47.7812 35.0382 47.7812 32.3125C47.7812 28.6575 46.3293 25.1522 43.7448 22.5677C41.1603 19.9832 37.655 18.5313 34 18.5313ZM34 42.1562C32.0531 42.1562 30.1499 41.5789 28.5311 40.4973C26.9123 39.4156 25.6506 37.8783 24.9056 36.0795C24.1605 34.2808 23.9656 32.3016 24.3454 30.3921C24.7252 28.4826 25.6627 26.7286 27.0394 25.3519C28.4161 23.9752 30.1701 23.0377 32.0796 22.6579C33.9891 22.2781 35.9683 22.473 37.767 23.2181C39.5658 23.9631 41.1031 25.2248 42.1848 26.8436C43.2664 28.4624 43.8438 30.3656 43.8438 32.3125C43.8438 34.9232 42.8066 37.427 40.9606 39.2731C39.1145 41.1191 36.6107 42.1562 34 42.1562Z" fill="#6F6F6F"/>
                            </svg>
                        </div>
                    </div>
                }
            </div>
            <div>
                История посещений
            </div>
            <div>
                Документы
            </div>
        </div>
    );
};

export default PetForm;