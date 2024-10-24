## Kabaya

This Android app forwards incoming SMS messages to a specified Telegram chat, making it convenient to stay updated on messages sent to SIM cards you might not regularly check. The app routes messages based on the SIM card slot (not all two-sim phones support that) to separate Telegram users, ensuring that you receive messages in an organized manner.

### Features

* Route Messages by SIM-card Slot: The app can distinguish between Slot 1 and Slot 2 on your Android device, forwarding SMS messages to different Telegram users based on the slot from which they were received.

* Secure Integration: You provide **your** Telegram bot token, ensuring your messages are not sent to any third party other than Telegram. The app only needs the "Send and Receive SMS" permission, the token, and user IDs to function.

* SIM Card Differentiation: You can assign a custom name to each slot, making identifying messages when received in Telegram easier.

I designed this app for scenarios where you need to receive and access SMS messages remotely. For example, if you have an old Android phone with a SIM card from a different country, this app enables you to receive and read those messages through Telegram.

The app only needs a Telegram bot token and Telegram user IDs to function. Since only you control the bot token, your bot remains secure. Ensure your bot token is kept private and not shared with anyone else.

### Usage

To get started, you’ll need the following inputs:

1.	**Telegram Bot Token:** Obtain a bot token by creating a bot on [BotFather](https://t.me/botfather). This is used to authenticate the bot within Telegram. See Telegram documentation's "[Obtain Your Bot Token](https://core.telegram.org/bots/tutorial#obtain-your-bot-token)" section for more information.
2.	**Slot 1 Telegram ID**: The ID of the Telegram user to whom messages received on Slot 1 should be sent. (You can find your user ID using, for example, the [userinfobot](https://t.me/userinfobot).)
3.	**Slot 1 Name:** An optional name to help you distinguish messages received on Slot 1 (e.g., “French SIM”).
4.	**Slot 2 Telegram ID**: The ID of the Telegram user to whom messages received on Slot 2 should be sent.
5.	**Slot 2 Name**: An optional name to help you distinguish messages received on Slot 2 (e.g., “Work SIM”).

#### Example Configuration

Here’s an example configuration that you might use:

	•	Bot Token: 123456789:ABCdefGhIJKlmNOPqRStUvwxYz
	•	Slot 1 Telegram ID: 987654321
	•	Name: French SIM
	•	Slot 2 Telegram ID: 123456789
	• Name: Work SIM

In this setup, messages received on Slot 1 (e.g., your French SIM card) will be forwarded to Telegram user 987654321, while messages on Slot 2 (e.g., your Work SIM card) will be forwarded to Telegram user 123456789. The slot names provide a clear context for identifying which SIM card received the message.

## Installation

1.	Build the APK and install it on your Android phone.
2.	Start the app and grant permission to access SMS.
3.	Configure the inputs: Open the app and input the required fields (Bot Token, User IDs, Slot Names).
4.	PROFIT!

## Future Enhancements

* Support more phones (I tested it on Samsung S8)
* Support for additional Telegram configurations and customization options.
* Optional logging or message history within the app.

## License

This project is open-source under the MIT License.
