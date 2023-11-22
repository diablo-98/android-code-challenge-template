# Starting Your Code Challenge
### About the Tasks
The tasks in this challenge don't have very detailed instructions. We know our requirements might not be perfect.

If you have a better idea (like something more efficient or creative), feel free to use this as an opportunity to showcase it. You don't have to follow our descriptions exactly 👍

### About libraries
You are free to choose and use any libraries that you deem necessary to complete the tasks.

And if you haven't used a certain library (like Jetpack Compose) or aren't very confident with it, you can:
- Choose a different way of achieving the same result (for example, use `databinding` instead of Jetpack Compose)
- Take some time to learn it and then try it out

### Time
We haven't set a time limit. Take as much time as you need and don't stress.

But, we also don't want you to spend too much time on this. Here’s how long you should spend on this:
- 2~3 hours

# Tasks for the Joke App
This app shows a joke regarding engineering.

|Placeholder|Joke|
|----|----|
|![282440497-317714da-4083-4b05-844d-f9524052c326](https://github.com/WASSHAHiring/android-code-challenge-template/assets/40135056/df52fe31-602a-4507-8c4c-3c839569f653){width=300}|![282440504-74d57508-9923-453c-b292-84f3c7aeaa35](https://github.com/WASSHAHiring/android-code-challenge-template/assets/40135056/40dd2f20-a672-419f-8cd8-d0c32747ead0){width=300}|

### Current issues
- Currently, this app can only show one joke which is hard-coded in the app.


### Things to Do (TODO)
- Fetch a random joke from https://v2.jokeapi.dev/joke/Programming?type=single and display it when the user presses the "Update Joke" button.
  -  Every time the user presses the "Update Joke" button, the displayed joke should be updated from the API. 
- Add a local data source so the user can see the last-fetched joke even offline.
- If the joke could not be fetched from the API and had to be fetched from the local source, let the user know about it by adding another text component such as "Data was fetched from local". 
- Use dependency injection such as Hilt
- Display the `length` from the API under the `Joke` *only if* it’s more than 80
- Display how many words the joke sentence contains. For instance, "Debugging: Removing the needles from the haystack." contains 7 words then should show "Words: 7"
- Add tests (unit or UI) based on your code

---
### How It Should Look After You're Done

![282639136-0c4da4a3-7aca-4e2e-8ef5-623701e677d2](https://github.com/WASSHAHiring/android-code-challenge-template/assets/40135056/1b622838-d2fc-4ce1-ab40-1ccda3b4bd2e){width=30}


---

# How to Submit
To submit your work:
- Make a pull request
- Send us the link to your pull request 🙏
    - We'll give comments for the next steps
