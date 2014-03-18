<h1>Stop the SMSs</h1>
<p>Thanks for trying iQuit. We're sorry that we could not help you on your journey.</p>
<h2></h2>
<form action="../service/optout-form" method="post">
	<table>
		<tbody>
			<tr>
				<td>Please take a moment to tell us why you want to stop the SMSs:</td>
				<td><select name="why">
						<option value="decided_not_to_quit">I decided not to quit</option>
						<option value="cannot_quit">I cannot quit</option>
						<option value="too_many_smss">There were too many SMSs</option>
						<option value="smss_not_relevant">The SMSs were not relevant to me</option>
						<option value="other">Other</option>
				</select></td>
			</tr>
			<tr>
				<td>Other reason:</td>
				<td><input name="other_specify" type="text" /></td>
			</tr>
			<tr>
				<td>Any other feedback?</td>
				<td><textarea name="feedback"></textarea></td>
			</tr>
			<tr>
				<td>Please enter your cellphone number to stop the SMSs:</td>
				<td><input name="msisdn" type="text" /></td>
			</tr>
		</tbody>
	</table>
	<button id="submit" type="submit">Opt out</button>
</form>