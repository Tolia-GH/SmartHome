import React, { useState } from 'react';
import { TextField, Grid, Box, Avatar, Typography, Container, FormControl, InputLabel, Select, MenuItem, Button, CssBaseline } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import $ from 'jquery';
import { Copyright } from '../Copyright';

export default function SignUp() {
    const [state, setState] = useState({
        age: 0,
        gender: null,
        firstName: '?',
        lastName: '?',
        country: 'RUSSIA',
        city: 'Moscow',
        street: '?',
        phone: '',
        email: '?',
        password: '',
        phoneCode: null, // Default phone code for Russia
    });

    const handleChange = (event) => {
        const { name, value } = event.target;
        setState((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    function sendAccount(firstName, lastName, password, age, phoneCode, phone, email, gender) {
        $.ajax({
            url: 'api/signUp',
            method: 'POST',
            data: {
                password,
                firstName,
                lastName,
                gender,
                age,
                phone: phoneCode + phone,
                email,
            },
            success: (res) => {
                if (res.success) {
                    console.log('Account added!');
                    alert('Account add success');
                } else {
                    console.log('Account add failed!');
                    alert(res.message);
                }
            },
            error: (err) => {
                console.log('Request failed', err);
                alert('There was an error processing your request.');
            },
        });
    }

    const handleSubmit = (e) => {
        e.preventDefault(); // Prevent the default form submission
        sendAccount(state.firstName, state.lastName, state.password, state.age, state.phoneCode, state.phone, state.email, state.gender);
    };

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign up
                </Typography>
                <Box component="form" noValidate sx={{ mt: 3 }} onSubmit={handleSubmit}>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                autoComplete="fname"
                                name="firstName"
                                required
                                fullWidth
                                id="firstName"
                                label="First Name"
                                autoFocus
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                required
                                fullWidth
                                id="lastName"
                                label="Last Name"
                                name="lastName"
                                autoComplete="lname"
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                id="email"
                                label="Email Address"
                                name="email"
                                autoComplete="email"
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Grid container spacing={1} alignItems="center">
                                <Grid item xs={4}>
                                    <FormControl fullWidth>
                                        <InputLabel id="phoneCodeLabel">Phone Code</InputLabel>
                                        <Select
                                            value={state.phoneCode}
                                            inputProps={{
                                                name: 'phoneCode',
                                                id: 'phoneCode-native-simple',
                                            }}
                                            labelId="phoneCodeLabel"
                                            id="phoneCode"
                                            onChange={handleChange}
                                        >
                                            <MenuItem value="+7">+7 (RU)</MenuItem>
                                            <MenuItem value="+1">+1 (USA)</MenuItem>
                                            <MenuItem value="+44">+44 (UK)</MenuItem>
                                        </Select>
                                    </FormControl>
                                </Grid>
                                <Grid item xs={8}>
                                    <TextField
                                        required
                                        fullWidth
                                        id="phone"
                                        label="Phone Number"
                                        name="phone"
                                        autoComplete="phone"
                                        onChange={handleChange}
                                    />
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                autoComplete="new-password"
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                name="age"
                                label="Age"
                                id="age"
                                autoComplete="age"
                                onChange={handleChange}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <FormControl fullWidth>
                                <InputLabel>Gender</InputLabel>
                                <Select
                                    value={state.gender}
                                    fullWidth={true}
                                    onChange={(e) => setState({...state, gender: e.target.value})}
                                >
                                    <MenuItem value="MALE">Male</MenuItem>
                                    <MenuItem value="FEMALE">Female</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Sign Up
                    </Button>
                    <Button
                        type="button"
                        href="/"
                        fullWidth
                        variant="outlined"
                        sx={{ mt: 0, mb: 2 }}
                    >
                        Back
                    </Button>
                </Box>
            </Box>
            <Copyright sx={{ mt: 5 }} />
        </Container>
    );
}
