package com.nocommittoday.techswipe.subscription.infrastructure.dev;

import com.nocommittoday.techswipe.subscription.infrastructure.HtmlTagCleaner;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("develop")
class HtmlTagCleanerDevTest {

    private HtmlTagCleaner htmlTagCleaner = new HtmlTagCleaner();

    @Test
    void 토스() {
        String html = """
                <style data-emotion="css 1vn47db">.css-1vn47db{display:-webkit-box;display:-webkit-flex;display:-ms-flexbox;display:flex;-webkit-flex-direction:column;-ms-flex-direction:column;flex-direction:column;-webkit-flex:none;-ms-flex:none;flex:none;}</style><div class="css-1vn47db"><style data-emotion="css 14on8x8">.css-14on8x8{font-size:17px;letter-spacing:0em;line-height:1.6;font-weight:normal;color:var(--adaptiveGrey800);margin:24px 0 8px;}</style><p id="810315fd-be9d-46b1-a898-be3971ba1a36" class="css-14on8x8"><style data-emotion="css 1r3ko7u">.css-1r3ko7u{line-height:0;display:block;}</style><span class="css-1r3ko7u"><style data-emotion="css 1iisb9p">.css-1iisb9p{display:contents;line-height:1.6;}</style><span class="css-1iisb9p"><style data-emotion="css 1kxrhf3">.css-1kxrhf3{white-space:pre-wrap;}</style><span class="css-1kxrhf3">최근 프론트엔드 개발에서 사실상의 표준으로 자리매김 하는 기술이 있습니다. 바로 SSR, Server Side Rendering인데요. Next.js를 비롯한 다양한 프레임워크에서 SSR을 손쉽게 구축할 수 있는 솔루션을 제공하고 있습니다. 토스 또한 SSR을 도입하여 많은 사용자에게 빠르고 안정적인 경험을 제공하려고 노력하고 있는데요.</span></span></span></p><p id="39c0f11e-b87b-40a3-a786-0878658169c8" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">오늘은 SSR 아키텍처 운영을 위해 반드시 알아두어야 할, SSR 서버의 최적화와 관련된 이야기를 해보려 합니다. 최적화를 통해 토스는 서비스 운영에 필요한 SSR 서버의 수를 절감하여 비용을 개선할 수 있었습니다.</span></span></span></p><style data-emotion="css 1c1qox8">.css-1c1qox8{font-size:30px;letter-spacing:0em;line-height:1.55;font-weight:bold;color:var(--adaptiveGrey900);margin:40px 0 4px;}</style><h2 id="5ada695f-2ffa-4a7d-9112-934d4f846bf8" class="css-1c1qox8"><span class="css-1r3ko7u"><style data-emotion="css p4abj2">.css-p4abj2{display:contents;line-height:1.55;}</style><span class="css-p4abj2"><span class="css-1kxrhf3">SSR이란?</span></span></span></h2><p id="c8597994-1e88-47d9-b1ab-d1835fd1b514" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">SSR은 Server Side Rendering의 약자로, 화면의 렌더링이 서버에서 이루어지는 아키텍처를 의미합니다. 사전적인 의미는 이러하지만 일반적으로 현대의 SSR은 “첫 HTML 렌더링을 서버에서 처리하고, 이후의 렌더링 사이클은 클라이언트에서 처리”하는 하이브리드 형태의 SSR을 가리킵니다. Next.js, Astro 등의 현대적인 웹 프레임워크는 기본적으로 제공하는 아키텍처입니다. Static Site Generation이나 Dynamic SSR처럼 다양한 방식이 있습니다.</span></span></span></p><p id="c56de743-1ccf-4e36-852f-47d440d7e742" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">SSR은 “첫 HTML 렌더링을 서버에서 처리”하기 때문에, 사용자의 화면에 컨텐츠가 그려지는데 걸리는 시간(FCP, First Contentful Paint)가 더 짧습니다. 토스는 이전에 CSR(Client Side Rendering) 아키텍처를 사용하고 있었는데요. 사용자의 화면에 JavaScript 번들이 모두 다운로드된 다음 첫 렌더링을 실행하면서 인증, 데이터 요청 등의 과정을 거치다보니 화면이 렌더링되는 시간이 상대적으로 길었습니다.</span></span></span></p><style data-emotion="css of5acw">.css-of5acw{margin:24px 0 8px;text-align:center;}</style><figure id="7db779f1-fde1-4e91-944c-f30178083e0f" class="css-of5acw"><style data-emotion="css 1pgssrp">.css-1pgssrp{max-width:100%;border-radius:16px;}</style><img src="https://static.toss.im/ipd-tcs/toss_core/live/0e1e8cdd-fb37-4c8d-a28b-00c8526849c1/Untitled.png" alt="" class="css-1pgssrp"/></figure><p id="c1c295dd-e7d7-4a26-9113-59ab27ad2038" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">이때 SSR 아키텍처를 도입하게 되면서, 인증과 데이터 처리의 첫 과정이 서버에서 먼저 모두 이루어진 다음 사용자는 완성된 HTML을 받아보면서 로딩 속도를 상당히 감축할 수 있었습니다.</span></span></span></p><figure id="d4d22bcd-8c53-4528-8ded-1f9e231b4856" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/21df1285-a1f9-4c2b-9871-5eb0408b875a/Untitled.png" alt="" class="css-1pgssrp"/></figure><figure id="45bf050b-a430-434b-b5ab-b16e0457de85" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/e14dbf0a-28a3-4b84-a0c3-463a0c4b4637/inner-ssr-6.gif" alt="" class="css-1pgssrp"/></figure><p id="f6f13fd8-f5b8-4673-9ed3-4973f3ebdf55" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">하지만 공짜 점심은 없죠.</span><style data-emotion="css q3ktjb">.css-q3ktjb{white-space:pre-wrap;font-weight:bold;}</style><span class="css-q3ktjb"> SSR을 도입하면 SSR 서버를 구축해야 합니다.</span><span class="css-1kxrhf3"> Static Serving으로 처리가 가능한 CSR 아키텍처에서는 별도의 로직을 구현하는 서버를 만들 필요가 없습니다. 요청자가 요청한 위치의 파일을 돌려주기만 하면 됩니다. 하지만 SSR은 요청에서 필요한 정보를 파악하고, 적절한 페이지 파일을 가져와 렌더링을 처리한 후, 완성된 HTML과 JS 번들을 돌려주어야 합니다. 다행히 토스에서 사용하는 Next.js는 SSR 서버 구축을 아주 쉽게 할 수 있도록 도와줍니다. 토스 앱의 동작에 필요한 몇몇 미들웨어와 라우팅 로직을 커스텀 서버에 구현하는 정도로 마무리할 수 있었으니까요.</span></span></span></p><p id="f1034d45-4af0-41d3-aaac-9c9616938de6" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">그러나 서비스의 성장에 따라 SSR 서버로 유입되는 트래픽이 늘어나면서, 서버의 숫자도 상당히 늘어나게 되었습니다. 또한 SSR 아키텍처의 빠른 로딩이라는 이점을 위해 SSR을 도입하는 서비스의 숫자도 점점 늘어났습니다. 이에 따라 SSR 서버의 숫자가 관리하기 어려울 만큼 많아졌고, 이들의 숫자를 줄여야 한다는 문제 의식이 발생합니다. 트래픽을 줄일 수는 없기 때문에, 같은 트래픽에서 서버의 숫자를 감축하는 것은 한 대의 서버가 감당할 수 있는 최대 트래픽의 수를 늘린다는 뜻이 됩니다.</span></span></span></p><figure id="a09e8abe-93d7-49df-a65b-cfd45751eb2f" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/58f98235-15e5-495d-8b91-b3fe30f62b47/Untitled.png" alt="" class="css-1pgssrp"/></figure><h2 id="0f3407c1-152f-4650-b97b-e4b7f65969d9" class="css-1c1qox8"><span class="css-1r3ko7u"><span class="css-p4abj2"><span class="css-1kxrhf3">SSR 서버 성능 측정하기</span></span></span></h2><p id="19b2a772-3085-44ec-a291-91405a1d2676" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">“측정”은 성능 개선 작업에 매우 필수적인 요소이자, 실제 개선보다도 어쩌면 더욱 중요한 부분입니다. 가장 먼저 저희 팀은 SSR 서버의 성능을 반복적으로 측정할 수 있는 환경을 마련하는데 집중했는데요. 프론트엔드 개발팀에서 서버 성능에 집중적으로 투자하는 첫 경험이다보니 여러 시행착오가 있었습니다. 이를 보완하기 위해 서버 플랫폼 팀과 주기적인 미팅을 진행하여 서버 운영 및 성능 측정에 필요한 다양한 노하우를 공유받았고, 성공적으로 성능 최적화의 기반을 마련할 수 있었습니다.</span></span></span></p><style data-emotion="css 1feg9au">.css-1feg9au{font-size:24px;letter-spacing:0em;line-height:1.6;font-weight:bold;color:var(--adaptiveGrey900);margin:24px 0 4px;}</style><h3 id="00a7af22-f6bc-427c-a82c-7858a2df9e60" class="css-1feg9au"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">변인 통제하기</span></span></span></h3><p id="7aec4087-377b-4ddd-a0f0-c2a020dc02d4" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">컨테이너는 격리된 환경이니 성능에 영향을 주는 요소가 덜할 것이라고 착각할 수 있습니다. 하지만 컨테이너 또한 프로세스이기 때문에, 해당 프로세스가 실행되는 호스트 환경에 따라 성능의 차이를 가질 수 있습니다. 처음 저희 팀은 개발 서버의 K8S 클러스터에 SSR 컨테이너를 배포하고 성능을 측정했는데요. 배포 시마다 성능이 달라지는 현상을 겪었고 DevOps팀과 확인 했습니다. 그 결과 개발 서버의 Pod Scheduling에 특별한 제한이 없어서, Pod이 어떤 Node에 배치되는지에 따라 성능에 편차가 발생할 수 있다는 사실을 알았습니다.</span></span></span></p><figure id="d29698ea-7b62-41c1-8406-4855d89c5b84" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/e26e771d-9ec3-4de3-8bcf-8347d3b121fc/Screenshot_2024-06-11_at_15.38.37.png" alt="" class="css-1pgssrp"/></figure><p id="40ff561c-5d23-431a-b15d-28d048904729" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">이 그래프는 소스코드의 아무런 수정없이 재배포 전후로 성능을 테스트한 결과입니다. 성능이 거의 2배 가까이 차이가 나는 것을 볼 수 있는데요. Pod이 얼마나 여유로운 Node에 배치되는지에 따라 성능 차이가 극심하게 발생할 수 있고, 이런 상황에서는 정상적인 변인 통제와 신뢰할 수 있는 성능 측정이 어려워집니다.</span></span></span></p><p id="5cfa324f-33b4-4d4b-ad46-c10caae7785c" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">따라서 EC2 스팟 인스턴스를 하나 두고 해당 인스턴스에는 성능 측정만을 위한 Pod을 배포하도록 수정했습니다. 격리된 환경은 반복적인 배포에서도 서비스 소스코드의 변경을 제외한 어떠한 다른 변인도 존재하지 않기 때문에, 수정된 코드가 성능에 어떻게 영향을 미치는지 투명하게 확인할 수 있는 매우 중요한 환경이 됩니다.</span></span></span></p><figure id="abe4c7e6-a5dd-41c8-982b-9dc4928f7cfc" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/c3473c71-a052-4f43-a168-be1408b6edef/Untitled.png" alt="" class="css-1pgssrp"/></figure><h3 id="76a13e48-4b38-405a-926b-eaefb5cbebfe" class="css-1feg9au"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">RPS 이해하기</span></span></span></h3><p id="993be712-97dd-419f-87d4-a5859eeef3ce" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">RPS, Request Per Second(혹은 TPS)는 서버의 트래픽 소화 능력을 확인하는 가장 중요한 지표입니다. 정해진 시간 내에 얼마나 많은 양의 요청을 처리할 수 있는지 나타내는 값인데요. 따라서 이 값이 높을 수록 더 적은 서버로 더 많은 트래픽을 감당할 수 있게 됩니다. 일반적으로 테스트 환경에서는 테스트 프로그램을 사용해 Maximum RPS를 쏟아 부어서 서버의 최대 트래픽을 확인하는 방식을 사용합니다. </span></span></span></p><p id="94acf99d-b388-4b8f-b0e0-cc5474c15bd8" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">처음 성능 측정을 진행할 때는 이 Max RPS만 보고, CPU 등 다른 리소스 사용량의 변화를 제대로 측정하지 않는 실수가 있었습니다. 서버의 비용과 갯수는 결국 리소스의 사용량으로 환산되기 때문에, 같은 트래픽 대비 리소스 사용량의 절감을 반드시 확인해야 합니다. Max RPS를 2배 향상시켰지만 리소스도 2배 더 사용한다면 적절한 의미의 비용 최적화라고 보기는 어려울 수 있습니다.</span></span></span></p><p id="4f3964b9-8126-4396-b729-aa3360669e51" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">추가적으로 RPS 측정 시 주의해야 하는 것이 있는데요. Maximum RPS를 넘어서는 트래픽에 대해 서버는 정상적인 응답을 할 수 없게 됩니다. 일반적으로는 Timeout이 발생하는데, 이렇게 서버가 비정상 응답을 돌려주는 시점의 트래픽 부하를 Max RPS로 볼 수 있습니다. 이때, 내가 측정 중인 성능이 테스트 대상 서버의 것인지 아니면 측정 프로그램의 것인지 착각하면 안됩니다. 측정 프로그램이 만들어낼 수 있는 부하보다 서버가 수용할 수 있는 한계가 더 높다면 정상적인 측정이 이루어지지 않습니다. </span></span></span></p><p id="5f3726d3-4575-4de1-b61e-5ecd4399ac46" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">저희 팀은 Grafana팀이 제작한 K6을 사용해 성능을 측정했는데요. JavaScript로 테스트 스크립트를 작성할 수 있으며 다양한 테스트 시나리오 설정을 제공하여 유연하고 정확한 성능 측정이 가능했습니다.</span></span></span></p><figure id="d8ae513b-60cd-46d1-9b9d-096c9c397dcf" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/c4789a4d-5e59-4e19-808e-917fa1a2e568/Screenshot_2024-06-11_at_15.28.18.png" alt="" class="css-1pgssrp"/></figure><h3 id="6fd13b84-ac48-4ad0-8097-844eed7066cd" class="css-1feg9au"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">Event Loop 매트릭</span></span></span></h3><p id="7c78e981-805e-4941-b332-fd846c1f34e8" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">CPU나 메모리 사용 이외에도 Node.js에 특화된 매트릭도 확인해야 합니다. 일반적으로 가장 널리 알려진 것은 Event Loop Lag와 Event Loop Utilization입니다. 모두 Event Loop와 관련된 매트릭인데요. Event Loop은 주기적으로 메인 스레드를 점유하여 대기 중인 task를 처리합니다. 이때, 메인 스레드가 다른 작업으로 인해 점유되어 Event Loop가 대기하는 시간이 길어지는 것을 Event Loop Lag로 표현합니다. Lag이 높다면 특정한 작업이 메인 스레드를 오래 점유(block)하고 있다는 것으로, Node.js의 Event Loop 패턴을 적극적으로 활용하지 못하고 있다는 뜻이 됩니다. 마찬가지로 Event Loop Utilization은 Event Loop가 얼마나 바쁘게 작업을 하고 있는지 나타냅니다. 0과 1 사이의 값을 갖는데, 1에 가까울 수록 Event Loop가 쉬는 시간 없이 항상 어떤 작업을 처리하고 있다는 뜻이 됩니다. 만약 서버가 이미 Max RPS에 도달하여 더 이상의 트래픽을 받지 못하고 있는데, Event Loop Utilization이 1보다 낮은 상태라면 Loop를 꽉 채워 사용하지 못하고 있다는 뜻이 됩니다.</span></span></span></p><p id="84877041-c3d2-4687-8a39-cca127c57066" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">Event Loop Uitilization은 비교적 최근에 등장한 개념입니다. 이 매트릭의 등장 배경은 </span><style data-emotion="css iynyr0">.css-iynyr0{white-space:pre-wrap;cursor:pointer;color:var(--adaptiveGrey600);-webkit-text-decoration:underline!important;text-decoration:underline!important;}</style><a target="_blank" rel="noreferrer noopener" class="css-iynyr0" href="https://nodesource.com/blog/event-loop-utilization-nodejs/">Introduction to Event Loop Utilization in Node.js</a><span class="css-1kxrhf3"> 아티클에 더욱 심도있게 소개되어 있습니다.</span></span></span></p><h2 id="db3e2000-50b9-4a0e-943a-04024ffa8da3" class="css-1c1qox8"><span class="css-1r3ko7u"><span class="css-p4abj2"><span class="css-1kxrhf3">반복 측정</span></span></span></h2><p id="5f74844b-1eb0-4967-92b1-96aa64c66436" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">다양한 수정사항이 지금 이 순간에도 서비스에 반영됩니다. 이는 곧 성능의 변동을 의미하기도 합니다. 만약 성능이 조금씩 하락하는 것을 눈치채지 못하고 있다가 어느 순간 이를 개선하려 한다면, 어디서부터 개선을 시도해야 하는지 막막할 겁니다. 따라서 변동사항이 있을 때 벤치마크를 통해 성능의 하락과 증가를 확인하는 것은 실질적인 최적화만큼이나 중요한 과제입니다. 저희 팀은 상기한 격리 환경과 매트릭 측정을 PR 머지 시마다 진행하도록 만들어서, 매번 서비스에 변경사항이 있을 때마다 성능의 변화를 그래프로 나타내도록 만들었습니다.</span></span></span></p><figure id="23d7ad6f-8594-4f8d-97f7-ec0ac83e8021" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/0c019a11-1209-48e1-8e1d-0bc052e2055b/Screenshot_2024-06-11_at_14.37.00.png" alt="" class="css-1pgssrp"/></figure><h2 id="9f76cf08-991d-4146-aa31-b3e32dee9d24" class="css-1c1qox8"><span class="css-1r3ko7u"><span class="css-p4abj2"><span class="css-1kxrhf3">최적화와 Profiling</span></span></span></h2><p id="0e987082-c6cc-4fed-802e-bdb55781f163" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">측정 환경이 모두 마련되었다면, 이제부터는 성능 저하의 의심 지점이나 개선 가능한 지점을 파악하고 하나씩 개선해볼 차례입니다. 저희 팀은 먼저 아키텍처를 몇몇 굵은 컴포넌트 단위로 쪼개고, 각각의 컴포넌트가 도입될 때마다 순정 상태로부터 얼마나 성능 하락이 발생하는지를 측정했습니다. 이 과정은 CPU Profiling을 이용하여 어떤 작업이 얼마 동안 메인 스레드를 점유하고 있는지 확인하는 방식으로 처리됩니다. </span></span></span></p><h3 id="84a4b649-3bcc-4df9-92ec-287e752a26d5" class="css-1feg9au"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">Opentelemetry 추적</span></span></span></h3><p id="2cf07b21-b0cc-4e06-a00a-0a23e3ec28ff" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">Next.js는 OpenTelemetry를 사용해, 렌더링 과정에서 발생하는 오버헤드 퍼포먼스를 측정할 수 있습니다. 그런데 이 기능은 최신 버전에서만 제공되고 있고 현재 저희 팀에서 사용 중인 버전에는 제공되지 않았습니다. 최신 버전에 수정된 hook을 직접 현재 사용중인 버전에 삽입한 후 아래와 같이 매트릭을 측정했습니다.</span></span></span></p><p id="04bdba73-acee-4e37-a92e-782ab9b5ca55" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">이를 통해, 몇몇 부분에서 성능 저하가 발생하고 있음을 확인했습니다. 개선된 부분 중 몇 가지 사례를 소개합니다.</span></span></span></p><h3 id="7105cd5c-7900-479e-8186-5430fecba795" class="css-1feg9au"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">Prefetch와 Serializing</span></span></span></h3><p id="334a9eff-ca6c-4313-b547-bd3cc4c8f4f2" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">위에 설명한 Opentelemetry로 SSR 서버의 성능을 확인하면 </span><code style="font-family:Consolas, Menlo, Monaco, source-code-pro, &quot;Courier New&quot;, monospace;font-size:0.9em;background-color:var(--adaptiveOpacity100);border:1px solid var(--adaptiveOpacity200);border-radius:3px"><style data-emotion="css 1mjnzsq">.css-1mjnzsq{white-space:pre-wrap;color:var(--adaptiveGrey800);}</style><span class="css-1mjnzsq">getInitialProps</span></code><span class="css-1kxrhf3"> 단계에서 매우 많은 시간이 소요되는 것을 알 수 있습니다. </span><code style="font-family:Consolas, Menlo, Monaco, source-code-pro, &quot;Courier New&quot;, monospace;font-size:0.9em;background-color:var(--adaptiveOpacity100);border:1px solid var(--adaptiveOpacity200);border-radius:3px"><span class="css-1mjnzsq">getInitialProps</span></code><span class="css-1kxrhf3">는 페이지 렌더링 전에 필요한 데이터를 가져오는 작업을 수행하는데요.</span></span></span></p><figure id="826430d7-bcd7-40f8-9216-e061cf8ebae3" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/0642549f-4226-4386-bd37-8b67526b90e2/Screenshot_2024-06-18_at_15.59.48.png" alt="" class="css-1pgssrp"/></figure><p id="2a7b2afe-5386-4bcd-b6f3-910ce07936f0" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">토스 프론트엔드는 React Query를 통해 Remote 데이터를 가져오고 있습니다. 이때 SSR 환경에서 prefetch된 데이터는 serializer를 통해 전달 가능한 형태로 변환된 후, 클라이언트에서 hydration을 거칠 때 다시 인스턴스화 됩니다. 이 작업은 Date 객체 등 곧바로 스트림이 불가능한 데이터 형식을 위해 삽입된 레이어인데요. 데이터 형식에 관계없이 serializer가 항상 진행되다보니 SSR 렌더링 과정에서 적지 않은 오버헤드를 차지하고 있었습니다.</span></span></span></p><p id="03724a1f-db73-489c-9fae-c26e709bba67" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">저희 팀은 이 serializer를 아예 없애기로 했습니다. 이 작업은 React Query의 queryClient cache를 hydrate 하기 위해 필요한데, 이 cache 자체가 stream 가능한 형식이 되도록 바꾸면 serializer가 개입하는 단계를 삭제할 수 있습니다.</span></span></span></p><h3 id="ee444960-e450-44de-9c02-b9235a974035" class="css-1feg9au"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">yarn 버전 업데이트</span></span></span></h3><p id="dd5958bd-4389-42b5-a0ef-78e7356ab12b" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">저희 팀은 yarn 3.2.4 버전과 pnp linker를 사용하고 있었는데요. pnp는 런타임에서도 모듈을 불러오고 해석하는 역할을 맡게 됩니다. 그런데 Profiler를 통해 모듈이 resolve되는 과정을 조사하던 중 이미 resolve된 Module에 대해 다시 import할 때 불필요한 오버헤드가 발생하고 있는 것을 발견했습니다. 이로 인해 SSR 서버의 성능이 다소 하락할 수 있었고, 최신 yarn을 설치했을 때는 발생하지 않는 것으로 보아 중간의 어떤 yarn 업데이트에서 해소된 적이 있었다는 것을 파악했습니다.</span></span></span></p><figure id="c06abbbf-ca90-40d9-8877-489f4bef7259" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/5d741224-d2f5-4e98-9729-6355d5c23d73/IMG_9798_(1).png" alt="" class="css-1pgssrp"/></figure><p id="9411b6ab-a375-4ffb-984f-ec1c1fac6a50" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">그래프를 보면 3.6.1에서 극적인 성능 개선이 발생했다는 것을 알 수 있습니다. 실제로는 서비스 구현에 따라 변화의 정도에 차이가 있겠지만 상당히 큰 개선인 것은 분명한데요. 이는 yarn pnp가 기존에 캐싱된 Module을 즉시 사용하지 않고 모듈에 대한 </span><a target="_blank" rel="noreferrer noopener" class="css-iynyr0" href="https://github.com/yarnpkg/berry/issues/4301">resolve를 재차 진행하는 불필요한 오버헤드</a><span class="css-1kxrhf3">를 갖고 있었기 때문이었습니다.</span></span></span></p><p id="038ebadd-317b-40a1-8282-99ac95cc25de" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">이 문제는 yarn 3.6.1에서 해결되었고, 저희 팀도 yarn 버전을 3.x 최신으로 업데이트하여 오버헤드를 제거할 수 있었습니다. </span></span></span></p><h3 id="dca93e85-3003-4875-b48b-6910b3c8c607" class="css-1feg9au"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">Express 제거</span></span></span></h3><p id="b6ed632e-d2a4-4f57-9bc3-c10d9413099f" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">또 다른 Next.js의 custom 서버에서 Express를 제거하는 일이었습니다. Express는 Node.js로 서버를 구성할 때 가장 많이 사용되는 프레임워크입니다. 그러나 토스 프론트엔드의 Next.js SSR 서버의 요구사항에 비해 다소 과한 기능들을 가지고 있었고, 미들웨어 등의 아키텍처가 오버헤드를 만들고 있습니다.</span></span></span></p><figure id="552eef9a-7703-49e4-98df-0f7700f9d538" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/92d84ddd-60e1-4ff0-9654-40956743af2c/inner-0625_(1).png" alt="" class="css-1pgssrp"/></figure><p id="c077f5a7-83b6-4f8c-b1c5-6b130f0af5c0" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">그래서 Express와 관련된 모든 코드를 제거하고 Node.js 내장 http 서버를 사용하는 패턴으로 변경하였고, CPU 사용량을 대략 4~5%p 낮출 수 있었습니다.</span></span></span></p><h2 id="ddda8df4-c91b-45ed-aa99-a7b8a044f67f" class="css-1c1qox8"><span class="css-1r3ko7u"><span class="css-p4abj2"><span class="css-1kxrhf3">개선 결과</span></span></span></h2><p id="9323f991-5532-41fc-8494-9ca1a88774b9" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">상기한 지점 이외에도 크고 작은 개선점들이 추가로 있었고, 종합적으로 CPU 사용률을 평균적으로 20% 가까이 줄임으로서 서버의 대수도 비례해서 줄일 수 있었습니다.</span></span></span></p><figure id="da9102e4-81c0-4b9a-bf71-7ade47d39ab9" class="css-of5acw"><img src="https://static.toss.im/ipd-tcs/toss_core/live/3ad09e9f-720c-4050-94d5-df1b3edc5ac5/Untitled.png" alt="" class="css-1pgssrp"/></figure><h2 id="728dd5f7-0815-4cff-8d91-63d6d2f8b659" class="css-1c1qox8"><span class="css-1r3ko7u"><span class="css-p4abj2"><span class="css-1kxrhf3">교훈</span></span></span></h2><p id="fb00321f-9ee0-40f9-ae51-fefc02769fa5" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">브라우저 JavaScript를 개발할 때와 서버 사이드 JavaScript를 개발하는 것은 비슷하지만 조금은 다른 특성을 갖고 있습니다. 브라우저는 (탭을 영원히 열어두지 않는 이상) 일시적인 프로세스가 활성화되고, 원격지로부터 코드를 받고, JavaScript 이외에 HTML과 CSS에 대해 염두해야 합니다. 한 편 서버 사이드 JavaScript는 반영구적으로 동작하는 프로세스, 네트워크와 호스트 환경에 대한 더욱 심도있는 이해, Node.js 등 사용 중인 런타임에 대한 이해가 매우 깊게 필요합니다. 이러한 이해를 바탕으로 성능을 측정할 수 있는 환경까지 마련된다면, 최적화는 어려운 일이 아닙니다. 그래서 만약 Node.js SSR 서버의 성능을 개선하고 싶다면, 다음의 과정을 한 번 고민해보시면 좋겠습니다.</span></span></span></p><style data-emotion="css hokoge">.css-hokoge{display:-webkit-box;display:-webkit-flex;display:-ms-flexbox;display:flex;-webkit-flex-direction:column;-ms-flex-direction:column;flex-direction:column;-webkit-flex:none;-ms-flex:none;flex:none;margin:24px 0 8px;padding:0;list-style:none;counter-reset:numberedList;}.css-hokoge ul,.css-hokoge ol{margin:16px 0 0;}.css-hokoge>li{counter-increment:numberedList;margin-bottom:16px;padding-left:24px;}.css-hokoge>li:last-of-type{margin-bottom:0;}.css-hokoge>li>span{position:relative;}.css-hokoge>li>span>:first-child::before{content:counter(numberedList) '.';font-weight:500;color:var(--adaptiveGrey800);position:absolute;left:-24px;}</style><ol class="css-hokoge"><style data-emotion="css 1hwiibq">.css-1hwiibq{font-size:17px;line-height:1.6;word-break:keep-all;letter-spacing:0em;font-weight:400;color:var(--adaptiveGrey800);}</style><li id="24960895-4e10-4d34-a3ba-5d7291bd514e" class="css-1hwiibq"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">현재의 런타임은 어떻게 구성되어 있는가? 네트워크 토폴로지는 어떻게 구성되어 있으며, 어떤 요청이 오가는가?</span></span></span></li><li id="2c481ca1-3a46-4257-b760-31c6ecf9856c" class="css-1hwiibq"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">측정이 필요한 성능은 무엇이 있는가? 측정할 수 있는가?</span></span></span></li><li id="fbf5c2f4-4b9a-41ef-acd3-c1f4c7801030" class="css-1hwiibq"><span class="css-1r3ko7u"><span class="css-1iisb9p"><span class="css-1kxrhf3">개선 전후를 확인할 수 있는, 변인이 통제된 재현 환경이 존재하는가?</span></span></span></li></ol><p id="56117a2a-5ffb-47b6-a0ff-afb000595fea" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"></span></span></p><p id="78d272f7-9bbe-4e24-ae30-aded183bec37" class="css-14on8x8"><span class="css-1r3ko7u"><span class="css-1iisb9p"></span></span></p></div>
                """;
        String content = htmlTagCleaner.clean(html);
        System.out.println(content);
    }
}