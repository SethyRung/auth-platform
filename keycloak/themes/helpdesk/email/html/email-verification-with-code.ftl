<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>Verify Your Email</title>
</head>
<body style="margin: 0;padding: 0;font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;background: #f9fafb;">
    <div class="email-container" style="max-width: 600px;margin: 0 auto;padding: 20px;">
        <table role="presentation" width="100%" bgcolor="#ffffff" style="border-radius: 12px;overflow: hidden;box-shadow: 0 1px 3px rgba(0,0,0,0.1);">
            <tr><td class="email-header" style="text-align: center;padding: 30px 0;border-bottom: 1px solid #e5e7eb;">
                <div class="logo" style="font-size: 24px;font-weight: 600;color: #1f2937;">${realmName!'Helpdesk'}</div>
            </td></tr>
            <tr><td class="email-body" style="padding: 40px 20px;">
                <h1 class="heading" style="font-size: 24px;font-weight: 600;color: #111827;margin: 0 0 16px 0;">Verify Your Email</h1>
                <p class="message" style="font-size: 15px;line-height: 1.6;color: #4b5563;margin-bottom: 24px;">
                    Use the verification code below to complete your email verification:
                </p>
                <div class="code-box" style="background: #f3f4f6;border: 2px dashed #d1d5db;padding: 20px;text-align: center;margin: 24px 0;border-radius: 8px;">
                    <div class="code" style="font-size: 28px;font-weight: 600;letter-spacing: 4px;color: #1f2937;font-family: monospace;">${code!'------'}</div>
                </div>
                <p class="message" style="font-size: 15px;color: #4b5563;">This code will expire in ${codeExpiration!'5 minutes'}.</p>
                <p class="message" style="color: #ef4444;">If you didn't request this, please ignore this email.</p>
            </td></tr>
            <tr><td class="email-footer" style="text-align: center;padding: 20px;color: #6b7280;font-size: 14px;border-top: 1px solid #e5e7eb;">
                <p style="margin: 0;">&copy; ${currentYear!'2026'} ${realmName!'Helpdesk'}</p>
            </td></tr>
        </table>
    </div>
</body>
</html>
