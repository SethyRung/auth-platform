<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>Test Email</title>
</head>
<body style="margin: 0;padding: 0;font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;background: #f9fafb;">
    <div class="email-container" style="max-width: 600px;margin: 0 auto;padding: 20px;">
        <table role="presentation" width="100%" bgcolor="#ffffff" style="border-radius: 12px;overflow: hidden;box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
            <tr><td class="email-header" style="text-align: center;padding: 30px 0;border-bottom: 1px solid #e5e7eb;">
                <div class="logo" style="font-size: 24px;font-weight: 600;color: #1f2937;">${realmName!'Helpdesk'}</div>
            </td></tr>
            <tr><td class="email-body" style="padding: 40px 20px;">
                <h1 class="heading" style="font-size: 24px;font-weight: 600;color: #111827;margin: 0 0 16px 0;">Test Email</h1>
                <p class="message" style="font-size: 15px;line-height: 1.6;color: #4b5563;margin-bottom: 16px;">
                    This is a test email from your Keycloak server.
                </p>
                <p class="message" style="font-size: 15px;color: #4b5563;margin-bottom: 16px;">
                    SMTP configuration: <strong>Success!</strong>
                </p>
                <div class="success-box" style="background: #ecfdf5;border-left: 4px solid #10b981;padding: 16px;margin: 24px 0;border-radius: 4px;">
                    <p class="message" style="color: #065f46;margin: 0;"><strong>✓ Email is working correctly</strong></p>
                </div>
                <p class="message" style="font-size: 14px;color: #4b5563;">
                    <strong>Realm:</strong> ${realmName!'Helpdesk'}<br>
                    <strong>Sent at:</strong> ${header.date!''}
                </p>
            </td></tr>
            <tr><td class="email-footer" style="text-align: center;padding: 20px;color: #6b7280;font-size: 14px;border-top: 1px solid #e5e7eb;">
                <p style="margin: 0;">&copy; ${currentYear!'2026'} ${realmName!'Helpdesk'}</p>
            </td></tr>
        </table>
    </div>
</body>
</html>
