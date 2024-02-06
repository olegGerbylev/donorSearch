// import { createContext } from 'react';
// import axios, { AxiosInstance } from 'axios';
// import {
//     AuthApi,
//     SessionInfoData,
// } from './api';
// import React = require('react');
//
//
// interface AppContextProps {
//   apiUrl: string;
//   history: any;
// }
//
// interface Rastjazka {
//   connectionError?: boolean;
//   versionUpdated?: boolean;
//   deskUpdated?: boolean;
// }
//
// export interface AppContextData {
//   sessionId?: string;
//   loading: boolean;
//   session?: SessionInfoData;
//   authApi: AuthApi;
//   axiosInstance: () => AxiosInstance;
//
//   login: (session: SessionInfoData) => void;
//   logout: () => void;
//   check: () => void;
//   location: string;
//   history: any;
//   errorHandler: (error: any) => void;
// }
//
// interface AppContextState {
//   session?: SessionInfoData;
//   loading: boolean;
// }
//
// const AppContext = createContext<AppContextData>({
//   loading: false,
//   authApi: new AuthApi(),
//   axiosInstance: () => axios.create(),
//   login: () => {},
//   logout: () => {},
//   check: () => {},
//   location: '/',
//   history: {},
//   errorHandler: () => {},
// });
//
// const getQueryVariable = (variable: string): string | undefined => {
//   const query = window.location.search.substring(1);
//   const vars = query.split('&');
//   for (let i = 0; i < vars.length; i++) {
//     const pair = vars[i].split('=');
//     if (decodeURIComponent(pair[0]) === variable) {
//       return decodeURIComponent(pair[1]);
//     }
//   }
//   return undefined;
// };
//
// class AppContextProvider extends React.Component<AppContextProps, AppContextState> {
//   constructor(props: AppContextProps) {
//     super(props);
//     this.state = {
//       loading: true,
//     };
//   }
//
//   componentDidMount(): void {
//     // console.log('AppContextProvider::mount');
//     const sessionFromQuery = getQueryVariable('session');
//     if (sessionFromQuery) {
//       localStorage.setItem('sessionId', sessionFromQuery);
//     }
//
//     this.checkSessionInfo();
//   }
//
//   componentWillUnmount(): void {
//     // console.log('AppContextProvider::unmount');
//   }
//
//   checkSessionInfo = () => {
//     let sessionId = localStorage.getItem('sessionId') || undefined;
//     // console.log('checkSessionInfo ' + sessionId);
//     if (!sessionId) {
//       this.setState({
//         session: undefined,
//         loading: false,
//       });
//     } else {
//       this.setState({
//         loading: true,
//       });
//       // console.log('Calling checkSession with sessionId=' + sessionId);
//       const authApi = new AuthApi({ basePath: this.props.apiUrl, accessToken: sessionId });
//       authApi
//         .checkSession()
//         .then(payload => {
//           this.setState({
//             session: payload.data,
//             loading: false,
//           });
//
//           try {
//               // @ts-ignore
//               if (window['$crisp']) {
//                   // @ts-ignore
//                   const $crisp = window['$crisp'];
//                   $crisp.push(["set", "user:email", payload.data.userEmail]);
//                   $crisp.push(["set", "session:data", ["accountId", payload.data.accountId]]);
//               }
//           } catch (e) {
//               console.log(e)
//           }
//             // console.log('check session', payload.data)
//         })
//         .catch(() => {
//           //localStorage.removeItem('sessionId');
//           this.setState({
//             session: undefined,
//             loading: false,
//           });
//         });
//     }
//   };
//
//   errorHandler = (error: any) => {
//     console.log('Default error handler', error);
//     if (error.response && error.response.data) {
//         const { config, data } = error.response;
//         if (config.url.endsWith('/check-session')) {
//           return;
//         }
//         if (data.error === 'INVALID_ACCESS_TOKEN') {
//           this.logout();
//         }
//         if (data.error === 'RESOURCE_NOT_FOUND' && data.args.entity !== 'default group') {
//           this.props.history.replace('/not-found?request=' + config.url);
//         }
//         if (data.error === 'SESSION_EXPIRED') {
//           this.props.topNotice.putMessage({ message: 'sessionExpired' });
//         }
//         if (data.error === 'INTERNAL_SERVER_ERROR') {
//           let isTestHost = window.location.hostname === 'localhost' || window.location.hostname.startsWith('test');
//           if (isTestHost) {
//             alert(`${data.message}\nuuid: ${data.uuid}\nmessage: ${data.args.message}`);
//           } else {
//             alert(`${data.message} uuid: ${data.uuid}`);
//           }
//         }
//         return Promise.reject(error);
//     } else {
//         return Promise.reject(error);
//     }
//   };
//
//   axiosInstance = () => {
//     const instance = axios.create();
//     instance.interceptors.response.use(
//       response => response,
//       error => {
//         return this.errorHandler(error);
//       }
//     );
//
//     return instance;
//   };
//
//   login = (session: SessionInfoData) => {
//     localStorage.setItem('sessionId', session.sessionId!!);
//     this.props.ws.send({ register: session.sessionId, windowId: this.props.ws.windowId });
//     this.setState({
//       session: session,
//       loading: false,
//     });
//     this.checkSessionInfo();
//   };
//
//   logout = () => {
//     if (this.state.session) {
//       if (this.state.session.superAdmin && this.state.session.accountId !== -1) {
//         const accountApi = new AccountApi({ basePath: this.props.apiUrl, accessToken: this.state.session.sessionId });
//         this.setState({
//           loading: true,
//         });
//         accountApi.logoutToSuperAccount().then(({ data }) => {
//           this.props.history.push('/sa/account');
//           this.login(data);
//         });
//       } else {
//         const authApi = new AuthApi({ basePath: this.props.apiUrl, accessToken: this.state.session.sessionId });
//         localStorage.removeItem('sessionId');
//         this.setState({
//           session: undefined,
//           loading: false,
//         });
//         authApi.logout();
//         this.props.history.push('/');
//       }
//     }
//   };
//
//   render() {
//     const sessionId = this.state.session != null ? this.state.session.sessionId : undefined;
//     return (
//       <AppContext.Provider
//         value={{
//           sessionId: sessionId,
//           loading: this.state.loading,
//           location: window.location.href,
//           session: this.state.session,
//           authApi: new AuthApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           accountApi: new AccountApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           userApi: new UserApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           profileApi: new ProfileApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           meetupApi: new MeetupApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           eventInfoApi: new EventInfoApi(
//               { basePath: this.props.apiUrl, accessToken: sessionId },
//               this.props.apiUrl,
//               this.axiosInstance()
//           ),
//           hallApi: new HallApi(
//               { basePath: this.props.apiUrl, accessToken: sessionId },
//               this.props.apiUrl,
//               this.axiosInstance()
//           ),
//           speakerApi: new SpeakerApi(
//               { basePath: this.props.apiUrl, accessToken: sessionId },
//               this.props.apiUrl,
//               this.axiosInstance()
//           ),
//           merchApi: new MerchApi(
//               { basePath: this.props.apiUrl, accessToken: sessionId },
//               this.props.apiUrl,
//               this.axiosInstance()
//           ),
//           newsApi: new NewsApi(
//               { basePath: this.props.apiUrl, accessToken: sessionId },
//               this.props.apiUrl,
//               this.axiosInstance()
//           ),
//           timeSlotApi: new TimeSlotApi(
//               { basePath: this.props.apiUrl, accessToken: sessionId },
//               this.props.apiUrl,
//               this.axiosInstance()
//           ),
//           visitorApi: new VisitorApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           groupApi: new GroupApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           badgeApi: new BadgeApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           imageApi: new ImageApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           configApi: new ConfigApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           regFormApi: new RegFormApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           chatConfigApi: new ChatConfigApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           receptionApi: new ReceptionApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           notificationApi: new NotificationApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           clockApi: new ClockApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           chatLogsApi: new ChatLogsApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           botApi: new BotApi(
//             { basePath: this.props.apiUrl, accessToken: sessionId },
//             this.props.apiUrl,
//             this.axiosInstance()
//           ),
//           axiosInstance: this.axiosInstance,
//           login: this.login,
//           logout: this.logout,
//           check: this.checkSessionInfo,
//           history: this.props.history,
//           errorHandler: this.errorHandler,
//         }}
//       >
//         {this.props.children}
//       </AppContext.Provider>
//     );
//   }
// }
//
// export { AppContext, AppContextProvider };
