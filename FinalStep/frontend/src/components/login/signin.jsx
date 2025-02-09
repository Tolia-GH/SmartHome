import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import $ from 'jquery'
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import backImg from '../../img/SmartHome.jpg'
import {createBrowserHistory} from "history";
import {useState} from "react";
import {Copyright} from "../Copyright";

export default function SignIn() {

    const [password,setPassword] = useState('')
    const [username,setUsername] = useState('')
    function sendAccount(username, password) {
        $.ajax({
            url: "api/signIn",
            method: "Post",
            data: {
                username: username,
                password: password
            },
            async: false,
            success: function (res) {
                if(res.success){
                    window.sessionStorage.setItem("username",username);
                    window.sessionStorage.setItem("password",password);
                    window.sessionStorage.setItem("id",res.id);
                    console.log(username + '\n' + password)
                    console.log(res.message)
                    alert(res.message)
                    const history = createBrowserHistory();
                    history.push('/main');
                }else {
                    alert(res.message);
                }
            }
        })
    }

    return (
        <Grid container component="main" sx={{ height: '100vh' }}>
            <CssBaseline />
            <Grid //背景图片
                item
                xs={12}
                sm={12}
                md={7}
                sx={{
                    backgroundImage: `url(${backImg})`,
                    backgroundRepeat: 'no-repeat',
                    backgroundColor: (t) =>
                        t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                }}
            />
            <Grid //登录页面
                item
                xs={12}
                sm={12}
                md={5}
                component={Paper}
                elevation={6}
                square
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'center',
                }}>
                <Box
                    sx={{
                        my: 8,
                        mx: 4,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign in
                    </Typography>
                    <Box component="form" noValidate sx={{ mt: 1 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="email"
                            label="Email Address or Phone Number"
                            name="email"
                            autoComplete="email"
                            autoFocus
                            onChange={(event) => (setUsername(event.target.value))}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            onChange={(event) => (setPassword(event.target.value))}
                        />
                        <Button
                            type="submit"
                            // href="/main"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 2, mb: 2 }}
                            onClick={() => {
                                sendAccount(username, password)
                            }}
                        >
                            Sign In
                        </Button>
                        <Button
                            type="submit"
                            href="/signup"
                            fullWidth
                            variant="outlined"
                            sx={{ mt: 0, mb: 1}}
                        >
                            Sign up
                        </Button>

                        <Copyright sx={{ mt: 5 }} />
                    </Box>
                </Box>
            </Grid>
        </Grid>
    );
}